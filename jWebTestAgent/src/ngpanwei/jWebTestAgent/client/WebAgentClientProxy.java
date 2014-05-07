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
package ngpanwei.jWebTestAgent.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Class to generate dynamic proxies for a given interface.
 * @author ngpanwei
 */
public class WebAgentClientProxy {
    /**
     * Create a proxy for target interface with given handler.
     * @param clazz
     * @param handler
     * @return
     */
	@SuppressWarnings("unchecked")
	public static <T> T newProxy(Class<T> interfaze,final Class<?> service, final IWebAgentClient client) {
		T proxy = (T)newProxy( Thread.currentThread().getContextClassLoader(),interfaze,service,client) ;
        return proxy ;
    }
    /**
     * Create a proxy for target interface.
     * @param clazz
     * @return
     */
	private static Object newProxy(ClassLoader pClassLoader, final Class<?> interfaze, 
					final Class<?> service, final IWebAgentClient client)
    {
		Object object = Proxy.newProxyInstance(pClassLoader, new Class[] { interfaze }, 
        	new InvocationHandler() {
            	public Object invoke(Object proxy, Method method,
                    Object[] args) throws Throwable {
            		try {
            			return handleInvocation(client, service, method, args) ;
            		} catch (WebAgentException e) {
            			throw new WebAgentException("Undefined "
            					+ service.getClass().getSimpleName()
            					+ "::" + method.getName()) ;
            		}
            	}
            });
    		return object ;
    }
	private static Object handleInvocation(IWebAgentClient client, 
			Class<?> serviceClass,Method method, Object[] args) throws Throwable {
		Method serviceMethod = getMethod(serviceClass,method,args) ;
		if(serviceMethod==null) {
			throw new WebAgentException("Cannot find method in service: "+method.getName()) ;
		}
		client.setTarget(serviceClass,serviceMethod,args) ; 
		return client.invoke(serviceMethod,args)  ;
	}

	private static Method getMethod(Class<?> service,Method method, Object[] args) {
		for(Method m : service.getMethods()) {
			if(m.getName().equals(method.getName())) {
				return m ;
			}
		}
		return null ;
	}
}
