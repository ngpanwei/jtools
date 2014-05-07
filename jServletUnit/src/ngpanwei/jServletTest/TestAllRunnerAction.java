/** 
  *  Copyright (c) 2014  Ng Pan Wei
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
package ngpanwei.jServletTest;

import javax.servlet.http.HttpServletRequest;

/**
 * Run all tests in the test suite/class.
 * @author ngpanwei
 */
public class TestAllRunnerAction extends ServletAction {
	/**
	 * key for this action.
	 */
	String getKey() {
		return "runAll" ;
	}
	/**
	 * description for this action.
	 */	
	String getDescription() {
		return "Run All Tests" ;
	}
	String doAction(TestServlet servlet,HttpServletRequest request) {
		String message = "Run All Tests<br/>" ;
		TestRunnerHandler handler = new TestRunnerHandler() ;
		handler.runTest(servlet.myTestClass);
		TestRunListener listener = handler.listener ;
		Integer numTest = listener.descriptions.size();
		message += "tests count = " + numTest.toString() + "<br/>" ;
		message += TestDescription.formatTestResults(listener.descriptions) ;
		return message ;
	}
}
