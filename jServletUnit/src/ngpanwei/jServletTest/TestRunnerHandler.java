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

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

public class TestRunnerHandler {
	TestRunListener listener ;
	
	public TestRunnerHandler() {
		listener = new TestRunListener() ;
	}

	public void runTest(Class<?> aClass,String methodName) {
        Request request = Request.method(aClass, methodName) ;
        runTest(request) ;
	}	
	public void runTest(Class<?> aClass) {
        Request request = Request.aClass(aClass) ;
        runTest(request) ;
	}
	public void runTest(Request request) {
		JUnitCore core = new JUnitCore();  
        core.addListener(listener);  
        core.run(request) ;
	}
}
