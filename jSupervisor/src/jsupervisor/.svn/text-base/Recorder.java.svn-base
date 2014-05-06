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
import java.util.Stack;

/**
 * For recording the execution of a scenario.
 * @author panwei
 */
public class Recorder {
	/**
	 * reference to Supervisor. 
	 */
	Supervisor _supervisor ;
	/**
	 * Caller stack ;
	 */
	Stack<Object> _callerStack ;
	/**
	 * Constructor.
	 * @param pSupervisor
	 */
	public Recorder(Supervisor pSupervisor) {
		_supervisor = pSupervisor ;
		_callerStack = new Stack<Object>() ;
		_callerStack.push(_supervisor) ;
	}
	/**
	 * Capture the execution as an invocation.
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 */
    public Object captureExecution(Object proxy, Method method,Object[] args) {
    	Object _caller = _callerStack.peek();
    	Invocation invocation = new Invocation(_caller,proxy,method,args,null) ;
    	_supervisor._invocations.add(invocation) ;
    	_callerStack.push(proxy);
    	_supervisor.echo("Capturing "+invocation.getString());
    	Object target = _supervisor._attachments.getTarget(proxy) ;
    	if(target==null) {
        	String message = "No Target Attached at " + getCallingClassSignature() ;
        	_supervisor.echo(message);
    		throw new ExecutionException(message) ;
    	}
    	Object theReturnValue = executeMethod(target, method, args) ;
    	invocation._result = new InvocationResult();
    	invocation._result.setReturn(theReturnValue) ;
    	_callerStack.pop();
		return theReturnValue ;
    }
    /**
     * Execute the method on the attached target.
     * @param pObject
     * @param pMethod
     * @param pArgs
     * @return
     */
	Object executeMethod(Object pObject, Method pMethod,Object[] pArgs) {
		Object result = null ;
		try {
			result = pMethod.invoke(pObject,pArgs) ;
		} catch (Exception e) {
			String message ;
			message = "Error Capturing " + getCallingClassSignature() ;
			_supervisor.echo(message) ;
			throw new ExecutionException(message) ;
		}
		return result ;
	}
	/**
	 * Record the execution sequence.
	 * @param pSession
	 */
	public void record(RecordingSession pSession) {
		if(pSession==null) {
			_supervisor.echo("Empty Recording Session at "
					+ getDefiningClassSignature()) ;
			return ;
		}
		try {
			pSession.recording() ;
		} catch (ExecutionException ignore) {
			_supervisor.echo("Error Recording at "
					+ getDefiningClassSignature()) ;
		}
	}
    /**
     * Get the signature of the caller.
     * This is wrapper over the watcher.
     * @return
     */
    protected String getCallingClassSignature() {
    	return Watcher.getCallingClassSignature(5) ;
    }	
    protected String getDefiningClassSignature() {
    	return Watcher.getCallingClassSignature(3) ;
    }	    
}
