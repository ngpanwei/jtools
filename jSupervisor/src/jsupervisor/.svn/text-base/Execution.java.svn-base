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
 * Execution of an invocation.
 * @author panwei
 */
public class Execution {
	public enum ExecutionResult {
		UNKNOWN, PASS, FAIL
	}
	Vector<String> _messages ;
	/**
	 * whether the execution is as expected.
	 */
	ExecutionResult _result ;
	/**
	 * Constructor.
	 */
	public Execution() {
		_messages = new Vector<String>() ;
		_result = ExecutionResult.UNKNOWN ;
	}
	/**
	 * Log a message.
	 * @param pMessage
	 */
	public void addMessage(String pMessage) {
		_messages.add(pMessage) ;
	}
	/**
	 * Get the messages as bulleted string.
	 * @param prefix the bullet
	 * @return
	 */
	public String getString(String prefix) {
		String str = "" ;
		for(String message : _messages) {
			str = str + prefix + message + "\n" ;
		}		
		return str ;
	}
	/**
	 * Set the execution result (pass or fail).
	 * @param result
	 */
	public void setResult(ExecutionResult result) {
		_result = result ;
	}
}
