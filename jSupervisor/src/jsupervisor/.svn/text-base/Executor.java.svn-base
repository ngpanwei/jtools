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

import jsupervisor.Execution.ExecutionResult;
import jsupervisor.Supervisor.ExecutionState;
import jsupervisor.verifiers.value.ValueVerifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Execute the invocation sequence.
 * @author panwei
 *
 */
public class Executor {
	/**
	 * Execution state.
	 */
	ExecutionState _state ;
	/**
	 * reference to Supervisor. 
	 */
	Supervisor _supervisor ;
	/**
	 * Current invocation.
	 */
	int _invocationIndex ;
	/**
	 * Constructor
	 * @param pSupervisor
	 */
	public Executor(Supervisor pSupervisor) {
		_supervisor = pSupervisor ;
	}
	/**
	 * Clear the executions in the invocations.
	 */
	public void clearExecutions() {
		for(Invocation invocation : _supervisor._invocations) {
			invocation._execution = new Execution() ;
		}
	}
	/***
	 * Get the current invocation.
	 * @return
	 */
	public Invocation currentInvocation() {
		return _supervisor._invocations.get(_invocationIndex) ;
	}
	/**
	 * Move to the next invocation.
	 * @return
	 */
	protected Invocation nextInvocation() {
		if(_invocationIndex+1>=_supervisor._invocations.size())
			return null ;
		_invocationIndex++ ;
		return _supervisor._invocations.get(_invocationIndex) ;
	}
	/**
	 * Peek the next invocation in the sequence.
	 * @return
	 */
	protected Invocation peekNextInvocation() {
		if(_invocationIndex+1>=_supervisor._invocations.size())
			return null ;
		return _supervisor._invocations.get(_invocationIndex+1) ;
	}
	
	/**
	 * Execute the invocations.
	 * @return null if successful, erroneous invocation otherwise.
	 */
	public ExecutionState executeInvocations() {
		try {
			_state = ExecutionState.PAUSED ;
			_invocationIndex = -1 ;
			Invocation invocation = peekNextInvocation();
			while(invocation!=null) {
				invocation = nextInvocation() ;
				invoke(invocation) ;
				invocation = peekNextInvocation();
			}
			_state = ExecutionState.COMPLETED ;
		} catch(ExecutionException e) {
			_state = ExecutionState.PAUSED ;
		}
		return _state ;
	}
	/**
	 * Determine the role and execute invocation accordingly.
	 * @param pExecutor
	 * @return
	 */
	public Execution invoke(Invocation pInvocation) {
		Object callerTarget = getTarget(pInvocation._caller) ;
		Object calleeTarget = getTarget(pInvocation._callee) ;
		if(callerTarget==null&&calleeTarget!=null)
			return invokeAsTester(pInvocation) ;
		_supervisor.echo("Skipping "+pInvocation.getString()) ;
		return null ;
	}
	/**
	 * execute the invocation as tester.
	 * @param pInvocation
	 * @return
	 */
	public Execution invokeAsTester(Invocation pInvocation) {
		_supervisor.echo("Invoking "+pInvocation.getString()) ;
		Execution result = null ;
		executeMethod(pInvocation,pInvocation._callee, 
								  pInvocation._method, 
								  pInvocation._params) ;
		result = currentInvocation()._execution ;
		return result ;
	}	
	/**
	 * Convenience method to getTarget()
	 * @param pProxy
	 * @return
	 */
	public Object getTarget(Object pProxy) {
		return _supervisor._attachments.getTarget(pProxy) ;
	}
	
	private boolean isIllegalInvocation(Throwable e) {
		if(e instanceof IllegalAccessException)
			return true;
		if(e instanceof InvocationTargetException) {
			InvocationTargetException ex = (InvocationTargetException)e ;
			if(ex.getTargetException() instanceof ExecutionException) {
				return true ;
			}
		}
		return false;
	}
	/**
	 * For hiding the clutter of exceptions.
	 * Note that the invoked method can throw exceptions as part of normal handling.
	 * IllegalAccessException and InvocationTargetException which are thrown by Method.invoke
	 * will be translated to a runtime exception - ExecutionException.
	 * @param pObject
	 * @param pMethod
	 * @param pArgs
	 * @return
	 */
	InvocationResult executeMethod(Invocation pInvocation,
		Object pObject, Method pMethod,Object[] pArgs) {
		InvocationResult actualResult = new InvocationResult() ;
		try {
			Object theReturn = pMethod.invoke(pObject,pArgs) ; 
			actualResult.setReturn(theReturn) ; 
		} catch (Throwable e) {
			if(isIllegalInvocation(e)) {
				if(pInvocation!=null) {
					logExecuteMethodException(e,pInvocation,pObject,pMethod,pArgs) ;
				}
			} else {
				InvocationTargetException ex = (InvocationTargetException)e ;
				actualResult.setException(ex.getTargetException());
			}
		}
		return actualResult ;
	}
	/**
	 * Log the execution exception.
	 * @param e
	 * @param pInvocation
	 * @param pObject
	 * @param pMethod
	 * @param pArgs
	 */
	private void logExecuteMethodException(Throwable e,Invocation pInvocation,
			Object pObject, Method pMethod,Object[] pArgs)  {
		String message = "Error executing " 
							+ pObject.getClass().getSimpleName() 
							+ "." + pMethod.getName();
		message += " " + e.getClass().getSimpleName() ;
		if(pInvocation!=null) {
			Execution ex = pInvocation._execution ;
			ex._result = ExecutionResult.FAIL ;
			ex.addMessage(message) ;
		}
		_supervisor.echo(message) ;
		throw new ExecutionException(message) ;
		
	}
	
    /**
     * Invoke the actual target.
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return the actual return value
     * @throws the actual exception
     */
    protected Object executeWithin(Object pProxy, Method pMethod,Object[] pArgs) 
    				throws Throwable {
		_supervisor.echo("Executing "+pProxy.getClass().getSimpleName()+"."
									 +pMethod.getName() + " at "+getCallingClassSignature()) ;
		Object target = getTarget(pProxy) ;
		if(isWithinCurrentInvocation(pProxy,pMethod,pArgs)) {
    		if(target==null)
    			return executeWithinAsMock(pProxy, pMethod, pArgs);
    		return executeWithinAsTester(target, pProxy, pMethod, pArgs);
    	}
		if(isWithinNextInvocation(pProxy,pMethod,pArgs)) {
    		if(target==null)
    			return executeWithinAsMock(pProxy, pMethod, pArgs);
			return executeWithinAsProbe(target, pProxy, pMethod, pArgs);
		}
		if(target==null) 
			return executeWithinAsReturn(pProxy, pMethod, pArgs);
		return this.executeWithinAsGlass(target,pProxy, pMethod, pArgs);
    }
    /**
     * 
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected Object executeWithinAsReturn(Object pProxy, Method pMethod,Object[] pArgs) {
    	Invocation currentInvocation = currentInvocation() ;
    	Execution ex = currentInvocation._execution ;
    	String message1 = "Scenario does not contain invocation for ->" 
    			 	+ pProxy.getClass().getSimpleName()+"."+pMethod.getName();
    	ex.addMessage(message1) ;
    	String message2 = "Called from " + getCallingClassSignature()  ;
    	ex.addMessage(message2) ;
    	ex._result = ExecutionResult.FAIL ;
    	throw new ExecutionException(message1 + "\n" + message2) ;
    }
    
    /**
     * Determine if execution as progressed to next in invocation sequence.
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected boolean isWithinNextInvocation(Object pProxy, Method pMethod,Object[] pArgs) {
    	Invocation nextInvocation = peekNextInvocation() ;
    	if(nextInvocation==null)
    		return false ;
    	return this.isInvocation(nextInvocation, pProxy, pMethod, pArgs);
    }
    /**
     * Checks if it is within current invocation.
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected boolean isWithinCurrentInvocation(Object pProxy, Method pMethod,Object[] pArgs) {
    	Invocation currentInvocation = currentInvocation() ;
		boolean result = isInvocation(currentInvocation,pProxy,pMethod,pArgs) ;
		return result ;
    }
    /**
     * Determines if the method execution is given invocation.
     * @param pInvocation
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected boolean isInvocation(Invocation pInvocation,
    			Object pProxy, Method pMethod,Object[] pArgs) {
    	if(pMethod.getName().equals(pInvocation._method.getName())==false)
    		return false ;
    	if(pArgs!=null&&pInvocation._params!=null) {
	    	if(pArgs.length!=pInvocation._params.length)
	    		return false ;
    	}
    	return true ;
    }
    /**
     * Execute transparently like a glass.
     * @param pTarget
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected Object executeWithinAsGlass(Object pTarget, Object pProxy, Method pMethod,Object[] pArgs) {
    	Invocation currentInvocation = currentInvocation() ;
		Object returnValue = null ;
		returnValue = this.executeMethod(currentInvocation,pTarget, pMethod, pArgs);
    	return returnValue ;
    }
    /**
     * Excecute as a probe.
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected Object executeWithinAsProbe(Object pTarget, Object pProxy, 
    									  Method pMethod,Object[] pArgs) 
    				throws Throwable {
    	Invocation invocation ;
    	invocation = nextInvocation();
    	checkCallArgs(invocation,pProxy,pMethod,pArgs) ;
		InvocationResult actualResult = null ;
		actualResult = executeMethod(invocation,pTarget, pMethod, pArgs);
		// check results against current invocation
		checkCallResult(invocation,pProxy,pMethod,pArgs,actualResult) ;
    	return actualResult.doReturn() ;
    }
    /**
     * Check the return.
     * @param pInvocation
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @param pActualResult
     */
    protected void checkCallResult(Invocation pInvocation,
			Object pProxy, Method pMethod,
			Object[] pArgs, InvocationResult pActualResult) {
    	Execution execution = pInvocation._execution ;
    	if(execution._result==ExecutionResult.FAIL)
    		return ;
    	if(pActualResult.isException()) {
        	pInvocation._result._exceptionVerifier.checkException(
        			pActualResult._exceptionObj,
        			pInvocation._result._exceptionObj,
    				execution,"Return values") ;
    	} else {
        	pInvocation._result._returnVerifier.checkValue(pActualResult._returnValue,pInvocation._result._returnValue,
    				execution,"Return values") ;
    	}
    	if(execution._result==ExecutionResult.FAIL)
    		throw new ExecutionException() ;
    }
    
    /**
     * Execute proxy invocation as mock
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected Object executeWithinAsMock(Object pProxy, Method pMethod,Object[] pArgs) {
    	Invocation invocation = nextInvocation();
    	checkCallArgs(invocation,pProxy,pMethod,pArgs) ;
    	return invocation._result._returnValue ;
    }
    /**
     * Check calling parameters.
     * @param pInvocation
     * @param pProxy
     * @param pMethod
     * @param pArgs
     */
    protected void checkCallArgs(Invocation pInvocation,
				Object pProxy, Method pMethod,Object[] pArgs) {
    	Execution execution = pInvocation._execution ;
    	if(pArgs==null)
    		return ;
    	if(pArgs.length!=pInvocation._params.length) {
    		String message = "Wrong Method executed at " 
    			+ Watcher.getCallingClassSignature(5);
    		throw new ExecutionException(message) ;
    	}
    	for(int i=0;i<pArgs.length;i++) {
    		ValueVerifier verifier = pInvocation._paramVerifiers.elementAt(i) ;
    		verifier.checkValue(pArgs[i],pInvocation._params[i],
    				pInvocation._execution,"Arguments "+i);
    	}
    	if(execution._result==ExecutionResult.FAIL)
    		throw new ExecutionException() ;
    }    
   
    /***
     * Execute the method as a tester.
     * @param pTarget
     * @param pProxy
     * @param pMethod
     * @param pArgs
     * @return
     */
    protected Object executeWithinAsTester(Object pTarget, Object pProxy, 
    									Method pMethod,Object[] pArgs) 
    			throws Throwable {
    	// track the current invocation as invocation
    	// lead to other invocations which moves the invocation index.
    	Invocation currentInvocation = currentInvocation() ; 
		InvocationResult actualResult = null ;
		actualResult = executeMethod(currentInvocation,pTarget, pMethod, pArgs);
		// check results against current invocation
		checkCallResult(currentInvocation,pProxy,pMethod,pArgs,actualResult) ;
		return actualResult.doReturn() ;
    }
    /**
     * Get the signature of the caller.
     * This is wrapper over the watcher.
     * @return
     */
    protected String getCallingClassSignature() {
    	
    	String signature = Watcher.getCallingClassSignature(5) ;
    	StackTraceElement trace = Watcher.getCaller(
    			new String[]{"Executor" , "Proxifier", "Method","Supervisor"
    					    }) ;
    	signature = Watcher.getSignature(trace);
    	if(signature.contains("null")) {
    		System.err.println(signature);
    	}
    	return signature ;
    }
}
