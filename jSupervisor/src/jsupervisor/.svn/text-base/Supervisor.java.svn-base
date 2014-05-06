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

import java.util.Vector;

/**
 * Execution supervisor to control and observe target objects. 
 * Supervisor creates proxies of target objects and monitors target objects
 * through the proxies.
 * @author panwei
 */
public class Supervisor {
	/**
	 * Supervision occurs in several mode.
	 */
	public enum Mode {
		SETUP , // set up the invocation sequence
		RECORD, // record the interaction sequence
		PLAY    // play the invocation sequence
	}
	public enum ExecutionState {
		PAUSED, COMPLETED
	}	
	/**
	 * List of invocations, which can be hand-crafted or recorded.
	 * Invocations are then executed and monitored
	 */
	Vector<Invocation> _invocations ;
	/**
	 * To bind proxies to target objects.
	 */
	Attachments _attachments ;
	/**
	 * Supervision mode ;
	 */
	protected Mode _mode ;
	/**
	 * Whether to echo execution.
	 */
	boolean _echo ;
	/**
	 * Helper class to create proxies.
	 */
	Proxifier _proxifier ;
	/**
	 * Object to define the invocation sequence
	 */
	protected Specifier _specifier ;
	/**
	 * Object to execute the invocation sequence.
	 */
	protected Executor _executor ;
	/**
	 * Object to record invocation sequence.
	 */
	protected Recorder _recorder ;
	/**
	 * Classes ignored by watcher.
	 */
	private static String[] _ignoredWatchers ;
	/**
	 * Constructor.
	 */
	public Supervisor() {
		_invocations = new Vector<Invocation>() ;
		_attachments = new Attachments() ;
		_mode = Mode.SETUP ;
		_proxifier = new Proxifier(this) ;
		_specifier = new Specifier(this) ;
		_executor = new Executor(this) ;
		_recorder = new Recorder(this);
		// set up for watcher
		_ignoredWatchers = new String[1];
		_ignoredWatchers[0] = Supervisor.class.getSimpleName();
	}
	/**
	 * Get the specifier
	 * @return
	 */
	public Specifier getSpecifier() {
		return _specifier ;
	}
	/**
	 * Attach target object to proxy.
	 * @param pProxy
	 * @param pImplementation
	 */
	public void attach(Object pProxy,Object pTarget) {
		_attachments.attach(pProxy, pTarget) ;
	}
	
	/**
	 * set up the monitoring sequence.
	 */
	public void reset() {
		_mode = Mode.SETUP ;
		_echo = false;
		_invocations = new Vector<Invocation>() ;
	}
	/**
	 * Turn echo on or off.
	 * @param pValue
	 */
	public void setEcho(boolean pValue) {
		_echo = pValue ;
	}
	/**
	 * Echo a message on the console.
	 * @param pMessage
	 */
	public void echo(String pMessage) {
		Watcher.log(_ignoredWatchers,pMessage) ;
	}
	/**
	 * Play the invocation sequence.
	 */
	public ExecutionState play() {
		_mode = Mode.PLAY ;
		_executor.clearExecutions() ;
		return _executor.executeInvocations() ;
	}
	/**
	 * Get the current execution.
	 * @return
	 */
	public Invocation currentInvocation() {
		return _executor.currentInvocation() ;
	}
	/**
	 * Start a recording session.
	 */
	public void record(RecordingSession pSession) {
		_mode = Mode.RECORD ;
		_recorder.record(pSession) ;
	}
	/**
	 * print the invocation sequence.
	 */
	public void print() {
		_specifier.print();
	}
    /**
     * Create a proxy for target interface.
     * @param clazz
     * @return
     */
	@SuppressWarnings("unchecked")
	public Object newProxy(Class clazz) {
        return _proxifier.newProxy(clazz);
    }
}
