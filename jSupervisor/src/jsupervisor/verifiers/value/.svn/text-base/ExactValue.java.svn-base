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

package jsupervisor.verifiers.value;

import jsupervisor.Execution;
import jsupervisor.Execution.ExecutionResult;

/**
 * Verifier that ensures that actual value equals the expected value
 * @author panwei
 */
public class ExactValue extends ValueVerifier {
	/**
	 * compares expected and actual.
	 */
	public void checkValue(Object pActual, Object pExpected,
			Execution pExecution, String pMessage) {
		if (pActual == null && pExpected == null) {
			pExecution.setResult(ExecutionResult.PASS) ;
			pExecution.addMessage(pMessage + " are both null.");
			return ;
		} 
		if (pActual != null && pExpected != null) {
			if (pActual.equals(pExpected)) {
				pExecution.setResult(ExecutionResult.PASS) ;
				pExecution.addMessage(pMessage + " are the same.");
				return ;
			}
			pExecution.setResult(ExecutionResult.FAIL) ;
			pExecution.addMessage(pMessage + " are different.");
			return ;
		} 
		if(pActual==null && pExpected!= null) { 
			pExecution.addMessage(pMessage + " actual is null.");
			pExecution.setResult(ExecutionResult.FAIL) ;
			return ;
		}
		if(pExpected==null && pActual!= null) { 
			pExecution.addMessage(pMessage + " expected null, but actually not null.");
			pExecution.setResult(ExecutionResult.FAIL) ;
			return ;
		}
	}
}
