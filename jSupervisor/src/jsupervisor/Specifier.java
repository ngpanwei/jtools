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

import jsupervisor.verifiers.except.ExceptionVerifier;
import jsupervisor.verifiers.value.ValueVerifier;

/**
 * For specifying the invocation sequence.
 * @author panwei
 */
public class Specifier {
	private static final String VERIFIER_MISMATCH_MESSAGE = "Number of verifiers do not match number of parameters.";
	/**
	 * Reference to the supervisor
	 */
	Supervisor _supervisor ;
	/**
	 * Temporary object for defining the caller during invocation setup.
	 */
	Object _caller ;
	/**
	 * Temporary object for the expected result
	 */
	InvocationResult _expectedResult ;
	/**
	 * Invocation being specified.
	 */
	Invocation _specifyingInvocation ;
	/**
	 * Constructor.
	 * @param pSupervisor
	 */
	public Specifier(Supervisor pSupervisor) {
		_supervisor = pSupervisor ;
		_caller = pSupervisor ;
		_expectedResult = new InvocationResult() ; 
	}
    /**
     * Set the caller during recording mode.
     * @param pCaller
     */
    public Specifier from(Object pCaller) {
    	_caller = pCaller ;
    	_expectedResult.reset() ;
    	return this;
    }
    
    /**
     * Get the caller.
     * @return
     */
    public Object getCaller() {
    	return _caller ;
    }
    /**
     * Set the return value for the next invocation.
     * @param _pReturnValue
     */
    public void expects(Object _pReturnValue) {
    	_expectedResult.setReturn(_pReturnValue) ;
    }
    /**
     * Set the return value for the next invocation.
     * @param _pReturnValue
     * @param _pResultVerifier
     */
    public void expects(Object _pReturnValue,ValueVerifier pVerifier) {
    	_expectedResult.setReturn(_pReturnValue) ;
    	_expectedResult._returnVerifier = pVerifier ;
    }
    /**
     * Set caller and return value at the same time.
     * @param pCaller
     * @param pReturnValue
     */
    public void expects(Object pCaller,Object pReturnValue) {
    	_caller = pCaller ;
    	_expectedResult.setReturn(pReturnValue) ;
    }
    /**
     * Set caller, return value and return verifier at the same time.
     * @param pCaller
     * @param pReturnValue
     * @param pVerifier
     */
    public void expects(Object pCaller,Object pReturnValue,ValueVerifier pVerifier) {
    	_caller = pCaller ;
    	_expectedResult.setReturn(pReturnValue) ;
    	_expectedResult._returnVerifier = pVerifier ;
    }
    /**
     * Set the expected exception.
     * @param pExceptionObject
     */
    public void excepts(Exception pExceptionObject) {
    	_expectedResult.setException(pExceptionObject);
    }
    /**
     * Set expected exception and exception verifier.
     * @param pExceptionObject
     * @param pExceptionVerifier
     */
    public void excepts(Exception pExceptionObject,ExceptionVerifier pExceptionVerifier) {
    	_expectedResult.setException(pExceptionObject);
    	_expectedResult._exceptionVerifier = pExceptionVerifier ;
    }
    /**
     * Set the caller and expected exception.
     * @param pCaller
     * @param pExceptionObject
     */
    public void excepts(Object pCaller,Exception pExceptionObject) {
    	_caller = pCaller ;
    	_expectedResult.setException(pExceptionObject);
    }
    /**
     * Set caller, expected exception and exception verifier.
     * @param pCaller
     * @param pExceptionObject
     * @param pExceptionVerifier
     */
    public void excepts(Object pCaller,Exception pExceptionObject,ExceptionVerifier pExceptionVerifier) {
    	_caller = pCaller ;
    	_expectedResult.setException(pExceptionObject);
    	_expectedResult._exceptionVerifier = pExceptionVerifier ;
    }
    /**
     * Get the expected invocation result.
     * @return
     */
    public InvocationResult getExpctedResult() {
    	return _expectedResult ;
    }
    /**
     * Specify method invocation into the invocation sequence.
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    public Object defineInvocation(Object proxy, Method method,Object[] args) {
    	InvocationResult theExpectedResult = _expectedResult.duplicate() ;
    	_specifyingInvocation = new Invocation(getCaller(),proxy,method,args,
    								theExpectedResult) ;
    	_supervisor._invocations.add(_specifyingInvocation) ;
    	_specifyingInvocation._source = getSpecificationSignature() ;
		return theExpectedResult._returnValue ;
    }
    /**
     * Set the expectation verifier for all arguments.
     * @param pValueVerifier
     */
    public void expectingAll(ValueVerifier pValueVerifier) {
    	for(int i =0;i<_specifyingInvocation._params.length;i++) {
    		_specifyingInvocation._paramVerifiers.add(pValueVerifier) ;
    	}
    }
    /**
     * Set the expectation verifiers the parameters in the invocation.
     * @param pVerifier
     */
    public void expecting(ValueVerifier ... pVerifiers) throws SpecificationException {
    	_specifyingInvocation._paramVerifiers.clear() ;
    	for(ValueVerifier verifier : pVerifiers){
        	_specifyingInvocation._paramVerifiers.add(verifier) ;
    	}
    }
	/**
	 * Print the invocation sequence.
	 */
	public void print() {
		for(Invocation invocation : _supervisor._invocations) {
			String message = invocation.getString() ;
			System.err.println(message) ;
		}
	}    
    /**
     * Get the signature of the caller.
     * This is wrapper over the watcher.
     * @return
     */
    protected String getSpecificationSignature() {
    	
    	String signature ;
    	StackTraceElement trace = Watcher.getCaller(
    			new String[]{"Specifier" , "Proxifier", "Supervisor",
    						 "Method", "NativeMethodAccessorImpl",
    						 "DelegatingMethodAccessorImpl",
    					    }) ;
    	signature = Watcher.getSignature(trace);
    	if(signature.contains("null")) {
    		System.err.println(signature);
    	}
    	return signature ;
    }	
}
