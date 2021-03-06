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
package jsupervisor.samples.basic.app;

/**
 * Sample authentication component.
 * @author panwei
 *
 */
public class MyAuthentication implements iAuthentication {
	/**
	 * Required interface to authentication server.s
	 */
	protected iAuthenticationServer _server ;
	/**
	 * Constructor.
	 */
	public MyAuthentication() {
		_server = null ;
	}
	/**
	 * Wire the authentication server.
	 * @param pServer
	 */
	public void setAuthenticationServer(iAuthenticationServer pServer) {
		_server = pServer ;
	}
	/**
	 * Authenticate username and password.
	 */
	@Override
	public boolean authenticate(String pUserName,String pPassWord) {
		if(_server!=null) {
			try {
				_server.connect("admin") ;
				return _server.authenticate(pUserName, pPassWord);
			} catch (ConnectionException ignore) {
				return false ;
			}
		}
		return false;
	}

}
