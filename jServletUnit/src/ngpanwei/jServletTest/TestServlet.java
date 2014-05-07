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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to manage the execution of tests. 
 * @author ngpanwei
 */
@SuppressWarnings("serial")
public abstract class TestServlet extends HttpServlet {

	private static final String ACTION = "action";

	/**
	 * The test class or test suite.
	 */
	Class<?> myTestClass = null ;
	
	/**
	 * Action map to handle different kinds of requests.
	 */
	HashMap<String,ServletAction> actionMap ;
	List<ServletAction> actions ;
	/**
	 * Default action ;
	 */
	ServletAction defaultAction = null ;
	/**
	 * Initialize the servlet.
	 */
	public void init() throws ServletException {
		actionMap = new HashMap<String,ServletAction>() ;
		actions = new ArrayList<ServletAction>() ;
		defaultAction = new SubjectViewAction() ;
		addAction(defaultAction) ;
		addAction(new TestAllRunnerAction()) ;
		addAction(new MethodTestAction()) ;
		initTest() ;
	}
	private void addAction(ServletAction action) {
		actionMap.put(action.getKey(),action) ;
		actions.add(action) ;
	}
	/**
	 * Abstract method to allow users to indicate which test class or test suite to run.
	 * This method should invoke initTest(Class<?> testClass).
	 */
	public abstract void initTest() ;
	/**
	 * Add the test class or test suite.
	 * @param testClass
	 */
	public void initTest(Class<?> testClass) {
		myTestClass = testClass;
	}

	/**
	 * Facade method to doAction.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request,response) ;
	}
	/**
	 * Facade method to doAction.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request,response) ;
	}
	/**
	 * Perform the request.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");
		String message = null ;
		if(myTestClass==null) {
			message = "Class/Suite under test not defined." ;
		} else {
			ServletAction action = getAction(request.getParameter(ACTION)) ;
			message = action.doAction(this,request) ;
		}
		// Print the results
		PrintWriter out = response.getWriter();
		out.println("<h1>Test Servlet : " + this.getServletName() +"</h1><hr/>");
		out.println(this.getActionList(request) +"<hr/>");
		out.println(message);
		out.close();
	}

	/**
	 * Get the action to perform.
	 * @param actionName
	 * @return
	 */
	ServletAction getAction(String actionName) {
		ServletAction action = actionMap.get(actionName) ;
		if(actionName==null) {
			return defaultAction ;
		}
		return action ;
	}
	String getActionList(HttpServletRequest request) {
		String message = "" ;
		message += "<table>" ;
		for(ServletAction action : actions) {
			if(action.isMenu()==false)
				continue ;
			message += "<tr>" ;
			message += "<td><a href='?action=" + action.getKey()+ "'>" + action.getDescription() + "</a></td>" ;
			message += "</tr>" ;
		}
		message += "</table>" ;
		return message ;
	}	
	public void destroy() {
		// do nothing.
	}
}
