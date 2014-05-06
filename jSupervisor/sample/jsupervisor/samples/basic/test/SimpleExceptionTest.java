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
import jsupervisor.samples.basic.app.ConnectionException;
import jsupervisor.samples.basic.app.MyAuthenticationServer;
import jsupervisor.samples.basic.app.iAuthenticationServer;
import junit.framework.TestCase;

/**
 * Test a single target that can throw exception.
 * @author panwei
 *
 */
public class SimpleExceptionTest extends TestCase {
	/**
	 * Supervisor to manage the collaboration.
	 */
	Supervisor supervisor ;
	/**
	 * Proxy to target authentication component.
	 */
	iAuthenticationServer myAuthenticationServer ;	
	/**
	 * Constructor to instantiate the supervision environment
	 * comprising supervisor and proxies.
	 */
	public SimpleExceptionTest() {
		supervisor = new Supervisor();
		myAuthenticationServer = (iAuthenticationServer)supervisor.newProxy(iAuthenticationServer.class);
	}
	/**
	 * Simple test to check detection of correctly thrown exception.
	 */
	public void testAuthentication_successful() {
		Watcher.start() ;
		// describe scenario.
		try {
			supervisor.reset() ;
			Specifier spec = supervisor.getSpecifier() ;
			spec.excepts(new ConnectionException("Cannot connect well."));
			myAuthenticationServer.connect("foo");
		} catch (Exception ignore) {
		}

		// assign test target
		MyAuthenticationServer target = new MyAuthenticationServer() ;
		supervisor.attach(myAuthenticationServer,target) ;
		
		// execute the scenario
		supervisor.setEcho(true) ;
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.COMPLETED);
	}	
	/**
	 * Simple test to detect check incorrectly thrown Exception.
	 */
	public void testAuthentication_unsuccessful() {
		Watcher.start() ;
		// describe scenario.
		try {
			supervisor.reset() ;
			Specifier spec = supervisor.getSpecifier() ;
			spec.excepts(new Exception("Unexpected Exception."));
			myAuthenticationServer.connect("foo");
		} catch (Exception ignore) {
		}

		// assign test target
		MyAuthenticationServer target = new MyAuthenticationServer() ;
		supervisor.attach(myAuthenticationServer,target) ;
		
		// execute the scenario
		supervisor.setEcho(true) ;
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.PAUSED);
		
		// get the failure cause
		Invocation invc = supervisor.currentInvocation();
		System.err.println("Failed at : "+invc.getString());
		System.err.println(invc.getExecution().getString(" * "));
	}	
}
