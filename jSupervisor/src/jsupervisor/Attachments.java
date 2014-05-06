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

package jsupervisor;

import java.util.HashMap;

/**
 * To associate concrete implementations with the proxy.
 * @author panwei
 */
public class Attachments {
	/**
	 * Mapping from proxy objects to target objects
	 */
	HashMap<String,Object> _attachments ;
	
	public Attachments() {
		_attachments = new HashMap<String,Object>() ;
	}
	/**
	 * Attach target object to proxy.
	 * @param pProxy
	 * @param pImplementation
	 */
	public void attach(Object pProxy,Object pTarget) {
		if(pProxy==null||pTarget==null)
			return ;
		_attachments.put(getProxyId(pProxy), pTarget) ;
	}
	private String getProxyId(Object pProxy) {
		String id = pProxy.getClass().getName() ;
		return id ;
	}
	
	/**
	 * get the target object attached to the proxy.
	 * @param pProxy
	 * @return
	 */
	public Object getTarget(Object pProxy) {
		if(pProxy==null)
			return null ;
		return _attachments.get(getProxyId(pProxy)) ;
	}

}
