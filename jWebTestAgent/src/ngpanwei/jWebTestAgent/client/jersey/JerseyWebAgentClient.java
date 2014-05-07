/** 
 *  Copyright (c) 2014  Ng Pan Wei
 *  
 *  Permission is hereby granted, free of charge, to any person 
 *  obtaining a copy of this software and associated documentation files 
 *  (the "Software"), to deal in the Software without restriction, 
 *  including without limitation the rights to use, copy, modify, merge, 
 *  publish, distribute, sublicense, and/or sell copies of the Software, 
 *  and to permit persons to whom the Software is furnished to do so, 
 *  subject to the following conditions: 
 *  
 *  The above copyright notice and this permission notice shall be 
 *  included in all copies or substantial portions of the Software. 
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 *  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 *  SOFTWARE. 
 */
package ngpanwei.jWebTestAgent.client.jersey;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ngpanwei.jWebTestAgent.client.IWebAgentClient;
import ngpanwei.jWebTestAgent.client.WebAgentException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
/**
 * JSON REST Client
 * @author ngpanwei
 * @see https://jersey.java.net/documentation/1.18/json.html
 */
public class JerseyWebAgentClient implements IWebAgentClient {
	Client client = null ;
	String baseUrl = null ;
	public JerseyWebAgentClient(String url) {
		resetClient() ;
		baseUrl = url ;
	}
	public ClientResponse doPost(String path,Object data) {
		WebResource webResource = client.resource(baseUrl+path);
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, data);
		if (response.getStatus() != 200) {
			System.err.println(response.toString());
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}		
		return response ;
	}
	public <T> T doPost(String path,Object data,Class<T> clazz) {
		ClientResponse response = doPost(path,data);
		if(clazz!=null) {
			T returnedData = response.getEntity(clazz) ;
			return returnedData ;
		} else {
			return null ;
		}
	}
	public Client getClient() {
		return client ;
	}
	public String getBaseUrl() {
		return baseUrl ;
	}
	private void resetClient() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(
				JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(clientConfig);
	}
	String url ;
	Consumes consumesTag ;
	Produces producesTag ;
	int index = 0 ;
	public void setTarget(Class<?> serviceClass,Method serviceMethod, Object[] args) {
		index = 0 ;
		String RESTPath = getParameterizedPath(serviceClass, serviceMethod);
		url = getInstantiatedPath(RESTPath,args) ;
		getDataDirective(serviceClass,serviceMethod) ;
	}
	private String getParameterizedPath(Class<?> serviceClass,
			Method serviceMethod) {
		Path servicePath = serviceClass.getAnnotation(Path.class) ;
		Path methodPath = serviceMethod.getAnnotation(Path.class) ;
		if(servicePath==null||methodPath==null) {
			throw new WebAgentException("Both "+serviceClass.getSimpleName() + " " 
						+ serviceMethod.getName() + " need @Path annotations") ;
		}
		String RESTPath = servicePath.value() + methodPath.value() ;
		return RESTPath ;
	}
	protected String getInstantiatedPath(String RESTPath, Object[] args) {
		Pattern ptrn = Pattern.compile("\\{[^}]*}");
		String instantiatePath = RESTPath ;
		Matcher mtchr = ptrn.matcher(instantiatePath);
		while(mtchr.find()) {
		    String match = mtchr.group();
		    String argument = (String)args[index++] ;
		    instantiatePath = instantiatePath.replace(match, argument) ;
		}
		return instantiatePath ;
	}
	private void getDataDirective(Class<?> serviceClass,Method serviceMethod) {
//		consumesTag = serviceMethod.getAnnotation(Consumes.class) ;
//		producesTag = serviceMethod.getAnnotation(Produces.class) ;
//		if(consumesTag==null&&producesTag==null) {
//			throw new WebAgentException(serviceClass.getSimpleName() + "." 
//					+ serviceMethod.getName() + " need either a @Consumes or @Produces annotations") ;
//		}
	}
	public Object invoke(Method serviceMethod, Object[] args) {
		// interface has no parameters
		if(args==null||args.length<=index) {
			return doPost(url,null,serviceMethod.getReturnType());
		}
		// all interface parameters are part of REST path
		if(index>=args.length) {
			return doPost(url,null,serviceMethod.getReturnType());
		// interface has argument that is a json object.
		} else {
			return doPost(url,args[index],serviceMethod.getReturnType());
		}
	}	
}
