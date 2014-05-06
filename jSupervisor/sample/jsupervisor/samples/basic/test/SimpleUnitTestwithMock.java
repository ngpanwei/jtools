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

import jsupervisor.Invocation;
import jsupervisor.Specifier;
import jsupervisor.Supervisor;
import jsupervisor.Watcher;
import jsupervisor.Supervisor.ExecutionState;
import jsupervisor.samples.basic.app.MyApp;
import jsupervisor.samples.basic.app.iApp;
import jsupervisor.samples.basic.app.iAuthentication;
import jsupervisor.verifiers.value.Value;
import junit.framework.TestCase;

/**
 * Test a scenario with a mock.
 * @author panwei
 */
public class SimpleUnitTestwithMock extends TestCase {
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
	public SimpleUnitTestwithMock() {
		supervisor = new Supervisor();
		myApp = (iApp)supervisor.newProxy(iApp.class);
		myAuthentication = (iAuthentication)supervisor.newProxy(iAuthentication.class);
	}
	/**
	 * Test to check that mock object can detect expected input values.
	 */
	public void testAuthentication_successful() {
		Watcher.start() ;
		// describe test scenario
		supervisor.reset() ;
		Specifier spec = supervisor.getSpecifier() ;
		myApp.start() ;
		spec.from(myApp) ;
		spec.expects(true) ;
		myAuthentication.authenticate("Mr Foo","bar");
		
		// attach targets
		MyApp targetApp = new MyApp() ;
		supervisor.attach(myApp, targetApp) ;
		targetApp.setAuthentication(myAuthentication) ;

		// execute the scenario
		supervisor.setEcho(true);
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.COMPLETED);
	}

	/**
	 * Test to check that mock object can detect unexpected input values.
	 */
	public void testAuthentication_unsuccessful() {
		Watcher.start() ;
		// describe test scenario
		supervisor.reset() ;
		Specifier spec = supervisor.getSpecifier() ;
		myApp.start() ;
		spec.from(myApp) ;
		spec.expects(true) ;
		myAuthentication.authenticate("Mr Foo","barn");
		
		// attach targets
		MyApp targetApp = new MyApp() ;
		supervisor.attach(myApp, targetApp) ;
		targetApp.setAuthentication(myAuthentication) ;

		// execute the scenario
		supervisor.setEcho(true);
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.PAUSED);
		
		// get the failure cause
		Invocation invc = supervisor.currentInvocation();
		System.err.println("Failed at : "+invc.getString());
		System.err.println(invc.getExecution().getString(" * "));
		
	}

	/**
	 * Test to check that mock object can relax detection of expected values.
	 */
	public void testAuthentication_successful_verifier() throws Exception {
		Watcher.start() ;
		// describe test scenario
		supervisor.reset() ;
		Specifier spec = supervisor.getSpecifier() ;
		myApp.start() ;
		spec.from(myApp) ;
		spec.expects(true) ;
		myAuthentication.authenticate("Mrs Foo","bars");
			spec.expecting(Value.Any,Value.Any) ;
		
		// attach targets
		MyApp targetApp = new MyApp() ;
		supervisor.attach(myApp, targetApp) ;
		targetApp.setAuthentication(myAuthentication) ;

		// execute the scenario
		supervisor.setEcho(true);
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.COMPLETED);
	}
	
}
