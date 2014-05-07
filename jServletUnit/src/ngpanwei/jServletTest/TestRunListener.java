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

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestRunListener extends RunListener
{
	List<TestDescription> descriptions ;
	TestDescription currentTestDescription ;
	public TestRunListener() {
		descriptions = new ArrayList<TestDescription>() ; 
	}
	
	/**
	 * Called before any tests have been run.
	 * */
	public void testRunStarted(Description description)	throws Exception
	{
		System.err.println("Number of testcases to execute : " + description.getClassName() + " - " + description.testCount());
	}

	/**
	 *  Called when all tests have finished
	 * */
	public void testRunFinished(Result result) throws Exception
	{
		System.err.println("Number of testcases executed : " + result.getRunCount());
	}

	/**
	 *  Called when an atomic test is aberr to be started.
	 * */
	public void testStarted(Description description) throws Exception
	{
		currentTestDescription = new TestDescription(description) ; 
		descriptions.add(currentTestDescription) ;
		System.err.println("Starting execution of test case : "+ description.getClassName() + " - " + description.getDisplayName());
		
	}

	/**
	 *  Called when an atomic test has finished, whether the test succeeds or fails.
	 * */
	public void testFinished(Description description) throws Exception
	{
		System.err.println("Finished execution of test case : "+ description.getMethodName());
	}

	/**
	 *  Called when an atomic test fails.
	 * */
	public void testFailure(Failure failure) throws Exception
	{
		System.err.println("Execution of test case failed : "+ failure.getMessage());
		currentTestDescription.isPass = false ;
		currentTestDescription.result = failure.getMessage() ;
	}

	/**
	 *  Called when a test will not be run, generally because a test method is annotated with Ignore.
	 * */
	public void testIgnored(Description description) throws Exception
	{
		System.err.println("Execution of test case ignored : "+ description.getMethodName());
	}
}