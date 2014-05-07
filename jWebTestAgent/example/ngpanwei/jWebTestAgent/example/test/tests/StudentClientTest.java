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
package ngpanwei.jWebTestAgent.example.test.tests;

import ngpanwei.jWebTestAgent.client.jersey.JerseyWebAgentClient;
import ngpanwei.jWebTestAgent.example.src.server.Student;

import org.junit.Test;

/**
 * Test class to invoke Jersey Reset Web Agent directly
 * rather than through a dynamic proxy.
 */
public class StudentClientTest {
	JerseyWebAgentClient jrClient = null ;
	public StudentClientTest() {
		jrClient = new JerseyWebAgentClient("http://localhost:8080/jCuke") ;
	}
	@Test
	public void runClient() throws Throwable {
		Student st = new Student("Adriana", "Smith", 12, 9);
		Student mySt = jrClient.doPost("/jsonServices/send",st,Student.class);
		System.out.println("*** " + mySt);
		
		Student st2 = jrClient.doPost("/jsonServices/print/john/",st,Student.class) ;
		System.out.println("*** " + st2);
	}
}
