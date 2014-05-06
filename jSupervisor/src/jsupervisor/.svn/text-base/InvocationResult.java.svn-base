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

import jsupervisor.verifiers.except.Except;
import jsupervisor.verifiers.except.ExceptionVerifier;
import jsupervisor.verifiers.value.Value;
import jsupervisor.verifiers.value.ValueVerifier;

/**
 * The expected result after making an invocation.
 * @author panwei
 */
public final class InvocationResult {
	/**
	 * Temporary object for defining the return object
	 */
	Object _returnValue ;
	/**
	 * Temporary object for defining the exception thrown.
	 * @param pSupervisor
	 */
	Throwable _exceptionObj;
	/**
	 * If the call would return an exception
	 */
	private boolean _isException ;
	/**
	 * Result verifier
	 */
	ValueVerifier 		_returnVerifier ;
	/**
	 * Exception verifier.
	 */
	ExceptionVerifier	_exceptionVerifier ;
	/**
	 * Constructor.
	 */
	InvocationResult() {
		_returnVerifier = Value.getDefault() ;
		_exceptionVerifier = Except.getDefaultExceptionVerifier() ;
		reset() ;
	}
	/**
	 * Reset the invocation result
	 */
	void reset() {
		_returnValue = null ;
		_exceptionObj = null ;
		_isException = false ;
	}
	/**
	 * Set the exception to be thrown
	 * @param pException
	 */
	void setException(Throwable pException) {
		_exceptionObj = pException ;
		_isException = true ;
		_returnValue = null ;
	}
	/**
	 * Set the return object
	 * @param pReturnValue
	 */
	void setReturn(Object pReturnValue) {
		_returnValue = pReturnValue ;
		_isException = false ;
		_exceptionObj = null ;
	}
	/**
	 * Checks if the expected result is an exception.
	 * @return
	 */
	boolean isException() {
		return _isException ;
	}
	/**
	 * Return the invocation result.
	 * @return
	 * @throws Throwable
	 */
	Object doReturn() throws Throwable {
		if(isException())
			throw _exceptionObj ;
		return _returnValue ;
	}
	/**
	 * Clone.
	 */
	InvocationResult duplicate() {
		InvocationResult theResult = new InvocationResult() ;
		theResult._returnValue = _returnValue ;
		theResult._exceptionObj = _exceptionObj ;
		theResult._isException = _isException ;
		theResult._returnVerifier = _returnVerifier ;
		theResult._exceptionVerifier = _exceptionVerifier ;
		return theResult ;
	}
}
