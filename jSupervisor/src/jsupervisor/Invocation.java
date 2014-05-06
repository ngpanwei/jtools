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

import java.lang.reflect.Method;
import java.util.Vector;

import jsupervisor.verifiers.value.Value;
import jsupervisor.verifiers.value.ValueVerifier;

/**
 * Data tracking invocation.
 * @author panwei
 *
 */
public class Invocation {
	/**
	 * Caller of this invocation. 
	 * This can be supervisor or some target object.
	 */
	public Object _caller;
	/**
	 * Proxy being invoked.
	 */
	public Object _callee ;
	/**
	 * Method being invoked.
	 */
	public Method _method ;
	/**
	 * Method parameters ;
	 */
	public Object[] _params ;
	/**
	 * Expected return value
	 */
	public InvocationResult _result ;
	/**
	 * parameter verification
	 */
	Vector<ValueVerifier> _paramVerifiers ;
	/**
	 * Source where this specification is found
	 */
	public String _source ;
	/**
	 * line number where this specification is found
	 */
	public int _line ;
	/**
	 * Execution of this invocation
	 */
	Execution _execution ;
	/**
	 * Constructor.
	 * @param pCaller
	 * @param pCallee
	 * @param pMethod
	 * @param pParams
	 */
	public Invocation(Object pCaller, Object pCallee,
			          Method pMethod,Object[] pParams,
			          InvocationResult pResult) {
		_caller = pCaller ;
		_callee = pCallee ;
		_method = pMethod ;
		_params = pParams ;
		_result = pResult ;
		_source = "(no source)" ;
		_line   = -1 ;
		_paramVerifiers = Value.getDefault(pParams) ;
		_execution = null ;
	}
	/**
	 * Get the execution log
	 * @return
	 */
	public Execution getExecution() {
		return _execution ;
	}
	/**
	 * Get the invocation string.
	 */
	public String getString() {
		String message = "" ;
		if(_caller.getClass().getInterfaces().length<1) {
			message += _caller.getClass().getSimpleName();
		} else {
			message += _caller.getClass().getInterfaces()[0].getSimpleName() ;
		}
		message += "->" ;
		message += _callee.getClass().getInterfaces()[0].getSimpleName() ;
		message += "." ;
		message += _method.getName();
		return message;
	}
	
}
