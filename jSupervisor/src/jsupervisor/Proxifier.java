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

import jsupervisor.Supervisor.Mode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Helper class to create proxies and dispatch proxy invocation.
 * @author panwei
 *
 */
public class Proxifier {
	/**
	 * Reference to supervisor.
	 */
	Supervisor _supervisor ;
	/**
	 * Constructor.
	 * @param pSupervisor
	 */
	public Proxifier(Supervisor pSupervisor) {
		_supervisor = pSupervisor ;
	}
	/**
	 * Dispatch proxy method invocations according to mode and execution. 
	 * @param proxy
	 * @param method
	 * @param args
	 */
    public Object handleInvocation(Object proxy, Method method,Object[] args) 
    			throws Throwable {
    	if(_supervisor._mode==Mode.SETUP) 
    		return _supervisor._specifier.defineInvocation(proxy,method,args);
    	if(_supervisor._mode==Mode.PLAY)
    		return _supervisor._executor.executeWithin(proxy,method,args);
    	if(_supervisor._mode==Mode.RECORD)
    		return _supervisor._recorder.captureExecution(proxy,method,args);
    	return null ;
    }

    /**
     * Create a proxy for target interface.
     * @param clazz
     * @return
     */
	@SuppressWarnings("unchecked")
	public Object newProxy(Class clazz) {
        return newProxy( Thread.currentThread().getContextClassLoader(),clazz);
    }
    /**
     * Create a proxy for target interface.
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object newProxy(ClassLoader pClassLoader, final Class clazz )
    {
        return Proxy.newProxyInstance(pClassLoader, new Class[] { clazz }, 
        	new InvocationHandler() {
            	public Object invoke(Object pProxy, Method pMethod,
                    Object[] pArgs) throws Throwable {
            		return handleInvocation(pProxy,pMethod,pArgs) ;
            	}
            });
    }

}
