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

import java.lang.reflect.Method;
import java.util.List;

import org.junit.runner.Description;

class TestDescription {
	public String className ;
	public String methodName ;
	public String displayName ;
	public boolean isPass ;
	public String result ;
	
	public TestDescription(Description desc) {
	    className = desc.getClassName() ;
	    methodName = desc.getMethodName() ;
	    displayName = desc.getDisplayName() ;
	    isPass = true ;
	    result = null ;
	}
	public TestDescription(Class<?> subject,Method method) {
	    className = subject.getName() ;
	    methodName = method.getName() ;
	    displayName = "" ;
	    isPass = true ;
	    result = null ;
	}
	public static String formatTestResults(List<TestDescription> testDescriptions) {
		String message = "<table>" ;
		for(TestDescription desc : testDescriptions) {
			message += "<tr>" ;
			message += "<td>" + desc.displayName + "</td>" ;
			if(desc.isPass) {
				message += "<td>" + "OK" + "</td>" ;
			} else {
				message += "<td>" + "Fail" + "</td>" ;
				
			}
			message += "</tr>" ;
		}
		message += "</table>" ;
		return message ;
	}
}
