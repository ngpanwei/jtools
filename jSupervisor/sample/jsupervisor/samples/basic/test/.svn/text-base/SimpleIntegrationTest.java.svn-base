/** 
  *  Copyright (c) 2010  Ng Pan Wei
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
package jsupervisor.samples.basic.test;

import jsupervisor.Specifier;
import jsupervisor.Supervisor;
import jsupervisor.Watcher;
import jsupervisor.Supervisor.ExecutionState;
import jsupervisor.samples.basic.app.MyApp;
import jsupervisor.samples.basic.app.MyAuthentication;
import jsupervisor.samples.basic.app.MyAuthenticationServer;
import jsupervisor.samples.basic.app.iApp;
import jsupervisor.samples.basic.app.iAuthentication;
import junit.framework.TestCase;

/**
 * Simple integration test of two classes.
 * @author panwei
 */
public class SimpleIntegrationTest extends TestCase {
	/**
	 * Supervisor to manage the collaboration.
	 */
	Supervisor supervisor ;
	/**
	 * Proxy to target app.
	 */
	iApp myApp ; 
	/**
	 * Proxy to target authentication component.
	 */
	iAuthentication myAuthentication ;
	/**
	 * Constructor to instantiate the supervision environment
	 * comprising supervisor and proxies.
	 */
	public SimpleIntegrationTest() {
		supervisor = new Supervisor();
		myApp = (iApp)supervisor.newProxy(iApp.class);
		myAuthentication = (iAuthentication)supervisor.newProxy(iAuthentication.class);
	}
	public void testAuthentication_successful() {
		Watcher.start() ;
		// describe scenario.
		supervisor.reset() ;
		Specifier spec = supervisor.getSpecifier() ;
		myApp.start() ;
		spec.expects(myApp,true) ;
		myAuthentication.authenticate("Mr Foo","bar");
		
		// assign test target
		MyApp targetApp = new MyApp() ;
		supervisor.attach(myApp,targetApp) ;
		targetApp.setAuthentication(myAuthentication) ;
		MyAuthentication targetAuthentication = new MyAuthentication() ;
		supervisor.attach(myAuthentication,targetAuthentication) ;
		targetAuthentication.setAuthenticationServer(new MyAuthenticationServer()) ;

		// print scenario
		spec.print();
		
		// execute the scenario
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.COMPLETED);
	}
}
