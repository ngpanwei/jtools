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
package jsupervisor.samples.practical;

import jsupervisor.Specifier;
import jsupervisor.Supervisor;
import jsupervisor.Watcher;
import jsupervisor.Supervisor.ExecutionState;
import jsupervisor.samples.basic.app.MyApp;
import jsupervisor.samples.basic.app.MyAuthentication;
import jsupervisor.samples.basic.app.MyAuthenticationServer;
import jsupervisor.samples.basic.app.iApp;
import jsupervisor.samples.basic.app.iAuthentication;
import jsupervisor.samples.basic.app.iAuthenticationServer;
import junit.framework.TestCase;

/**
 * Test the collaboration of several target objects.
 * @author panwei
 */
public class AuthenticationCollaborationTest extends TestCase {
	/**
	 * Supervisor to manage the collaboration.
	 */
	Supervisor supervisor ;
	/**
	 * Proxy to target app.
	 */
	iApp appProxy ; 
	/**
	 * Proxy to target authentication component.
	 */
	iAuthentication authProxy ;
	/**
	 * proxy to target authentication server.
	 */
	iAuthenticationServer serverProxy ;
	/**
	 * Constructor to instantiate the supervision environment
	 * comprising supervisor and proxies.
	 */
	public AuthenticationCollaborationTest() {
		supervisor 	= new Supervisor();
		appProxy 	= (iApp)supervisor.newProxy(iApp.class);
		authProxy 	= (iAuthentication)supervisor.newProxy(iAuthentication.class);
		serverProxy = (iAuthenticationServer)supervisor.newProxy(iAuthenticationServer.class);
	}
	/**
	 * We want to test the start up of my application 
	 * User --> App : App checks get the user name 
	 * App --> Authentication : App gets MyAuthentication to check if authentication valid.
	 * Authentication --> AuthentcationServer : checks with the authentication server
	 */
	public void authenticateUserWhenAppStarts() throws Exception {
		supervisor.reset() ;
		Specifier spec = supervisor.getSpecifier() ;
		
		appProxy.start() ;
		
		spec.expects(appProxy,true) ;
		authProxy.authenticate("Mr Foo","bar");
		
		spec.expects(authProxy,null) ;
		serverProxy.connect("admin");

		spec.expects(authProxy,true) ;
		serverProxy.authenticate("Mr Foo","bar");
	}
	/**
	 * Attach the concrete target implementations if any.
	 * @param appTarget
	 * @param authTarget
	 * @param authServerTarget
	 */
	public void attachTargets(MyApp appTarget,
				MyAuthentication authTarget,
				MyAuthenticationServer authServerTarget) {
		supervisor.attach(appProxy, appTarget) ;
		supervisor.attach(authProxy, authTarget) ;
		supervisor.attach(serverProxy, authServerTarget) ;
		if(appTarget!=null) 
			appTarget.setAuthentication(authProxy) ;
		if(authTarget!=null) 
			authTarget.setAuthenticationServer(serverProxy) ;
	}
	/**
	 * Test to check correct capturing of scenario definition.
	 */
	public void testScenarioDescription() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		supervisor.getSpecifier().print() ;
	}
	/**
	 * Test execution without concrete targets.
	 */
	public void testWithNoTargets() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		attachTargets(null,null,null) ;
		supervisor.play() ;
	}
	/**
	 * Test execution with first object with concrete implementation.
	 */
	public void testWithFirstTarget() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		MyApp myApp = new MyApp() ;
		attachTargets(myApp,null,null) ;
		supervisor.play() ;
	}

	/**
	 * Test execution with second object with concrete implementation.
	 */
	public void testWithSecondTarget() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		MyAuthentication targetAuthentication = new MyAuthentication() ;
		attachTargets(null,targetAuthentication,null) ;
		supervisor.play() ;
	}
	/**
	 * Test execution with third object with concrete implementation.
	 */
	public void testWithThirdTarget() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		MyAuthenticationServer targetAuthenticationServer = new MyAuthenticationServer() ;
		attachTargets(null,null,targetAuthenticationServer) ;
		supervisor.play() ;
	}
	/**
	 * Test execution with all object having concrete implementation.
	 */
	public void testWithAllTargets() throws Exception {
		Watcher.start() ;
		authenticateUserWhenAppStarts() ;
		MyApp targetApp = new MyApp() ;
		MyAuthentication targetAuth = new MyAuthentication() ;
		MyAuthenticationServer targetServer = new MyAuthenticationServer() ;
		attachTargets(targetApp,targetAuth,targetServer) ;
		supervisor.play() ;
	}
}
