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
package ngpanwei.jWebTestAgent.server;

import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class SimpleWebServer
{
    public static void main(String[] args) throws Exception
    {
        new SimpleWebServer().runServer();
    }

	private void runServer() throws Exception, InterruptedException {
		Server server = new Server(8080);
		WebAppContext webContext = getWebAppContext();
        server.setHandler(webContext); 
        server.start();
        server.join();
	}

	private WebAppContext getWebAppContext() {
		final URL warUrl = this.getClass().getClassLoader().getResource("ngpanwei");
		final String warUrlString = warUrl.toExternalForm();
		final String CONTEXTPATH = "/";
		WebAppContext context = new WebAppContext(warUrlString, CONTEXTPATH) ;
        context.setResourceBase("./web");
        context.setDescriptor("./web/WEB-INF/web.xml");
        context.setParentLoaderPriority(true);
		return context;
	}
}
