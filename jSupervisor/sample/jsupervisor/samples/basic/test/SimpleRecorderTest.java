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

import jsupervisor.RecordingSession;
import jsupervisor.Supervisor;
import jsupervisor.Supervisor.ExecutionState;
import jsupervisor.samples.basic.app.MyApp;
import jsupervisor.samples.basic.app.MyAuthentication;
import jsupervisor.samples.basic.app.MyAuthenticationServer;
import jsupervisor.samples.basic.app.iApp;
import jsupervisor.samples.basic.app.iAuthentication;
import jsupervisor.samples.basic.app.iAuthenticationServer;
import junit.framework.TestCase;

/**
 * For testing the recording of scenarios.
 * @author panwei
 */
public class SimpleRecorderTest extends TestCase {
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
	 * proxy to target authentication server.
	 */
	iAuthenticationServer myAuthenticationServer ;
	/**
	 * Constructor to instantiate the supervision environment
	 * comprising supervisor and proxies.
	 */
	public SimpleRecorderTest() {
		supervisor = new Supervisor();
		myApp = (iApp)supervisor.newProxy(iApp.class);
		myAuthentication = (iAuthentication)supervisor.newProxy(iAuthentication.class);
		myAuthenticationServer = (iAuthenticationServer)supervisor.newProxy(iAuthenticationServer.class);
	}
	/**
	 * Attach the concrete target implementations if any.
	 * @param pTargetApp
	 * @param pTargetAuthentication
	 * @param pTargetLicenseServer
	 */
	public void attachImplementations(MyApp pTargetApp,MyAuthentication pTargetAuthentication,
			MyAuthenticationServer pTargetLicenseServer) {
		supervisor.attach(myApp, pTargetApp) ;
		supervisor.attach(myAuthentication, pTargetAuthentication) ;
		supervisor.attach(myAuthenticationServer, pTargetLicenseServer) ;
		if(pTargetApp!=null) 
			pTargetApp.setAuthentication(myAuthentication) ;
		if(pTargetAuthentication!=null) 
			pTargetAuthentication.setAuthenticationServer(myAuthenticationServer) ;
	}
	/**
	 * Test the recording capability.
	 */
	public void testRecording() {
		MyApp targetApp = new MyApp() ;
		MyAuthentication targetAuthentication = new MyAuthentication() ; 
		MyAuthenticationServer targetAuthenticationServer = new MyAuthenticationServer();
		attachImplementations(targetApp,targetAuthentication,targetAuthenticationServer) ;
		
		// record execution sequence.
		supervisor.record(new RecordingSession() {
			public void recording() {
				myApp.start();
			}
		}) ;
		supervisor.echo("Done Recording");
		supervisor.getSpecifier().print() ;
		ExecutionState state = supervisor.play() ;
		assertEquals(state, ExecutionState.COMPLETED);
	}
}
