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

public class MethodTestAction  extends ServletAction {
	static final String TEST_METHOD = "testMethod";
	/**
	 * key for this action.
	 */
	String getKey() {
		return TEST_METHOD ;
	}
	/**
	 * description for this action.
	 */	
	String getDescription() {
		return "test a specific method" ;
	}
	boolean isMenu() {
		return false ;
	}	
	String doAction(TestServlet servlet,HttpServletRequest request) {
		String message = "Test A Specific Method<br/>" ;
		String className = request.getParameter("class") ;
		String methodName = request.getParameter("method") ;
		Class<?> subject = SubjectTraversal.findSubject(servlet.myTestClass, className) ;
		if(subject==null) {
			message += "Cannot find class : " + className + "<br/>" ;
		} else {
			TestRunnerHandler handler = new TestRunnerHandler() ;
			handler.runTest(subject, methodName);
			
			TestRunListener listener = handler.listener ;
			Integer numTest = listener.descriptions.size();
			message += "tests count = " + numTest.toString() + "<br/>" ;
			message += TestDescription.formatTestResults(listener.descriptions) ;
			
		}
		return message ;
	}

}
