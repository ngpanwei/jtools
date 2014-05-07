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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

public class SubjectTraversal {

	public static Class<?> findSubject(Class<?> subject,final String className) {
		if(subject.getName().equals(className))
			return subject ;
		final List<Class<?>> list = new ArrayList<Class<?>>() ;
		traverse(subject,new SubjectVisitor() {
			public boolean beforeClass(Class<?> subject) {
				if(subject.getName().equals(className))
					list.add(subject) ;				
				return false;
			}
			
		}) ;
		if(list.size()<1)
			return null ;
		return list.get(0) ;
	}
	
	public static void traverse(Class<?> subject,SubjectVisitor visitor) {
		if(isSuite(subject)) {
			traverseSuite(subject,visitor) ;
		} else {
			traverseClass(subject,visitor) ;
		}
	}
	public static void traverseSuite(Class<?> subject,SubjectVisitor visitor) {
		SuiteClasses suiteClasses = getSuiteClasses(subject) ;
		for(Class<?> suiteSubject : suiteClasses.value()) {
			traverse(suiteSubject,visitor) ;
		}
	}
	public static void traverseClass(Class<?> subject,SubjectVisitor visitor) {
		if(visitor.beforeClass(subject)==false) {
			return ;
		}
		Method[] methods = subject.getDeclaredMethods() ;
		for(Method method : methods) {
			visitor.beforeMethod(method) ;
		}
	}
	/**
	 * Determine if method is a test method.
	 * @param method
	 * @return
	 */
	public static boolean isTestMethod(Method method) {
		Test test = method.getAnnotation(Test.class) ;
		if(test==null)
			return false ;
		return true ;
	}
	/**
	 * Determine if subject is a Suite.
	 * @param subject
	 * @return
	 */
	public static boolean isSuite(Class<?> subject) {
		RunWith runWith = getRunWith(subject) ; 
		SuiteClasses suiteClasses = getSuiteClasses(subject) ;
		if(runWith!=null&&suiteClasses!=null) {
			return true ;
		}
		return false ;
	}
	/**
	 * get RunWith annotation.
	 * @param subject
	 * @return
	 */
	public static RunWith getRunWith(Class<?> subject) {
		Annotation annotation = subject.getAnnotation(RunWith.class) ;
		return (RunWith)annotation ;
	}
	/**
	 * get SuiteClasses annotation.
	 * @param subject
	 * @return
	 */
	public static SuiteClasses getSuiteClasses(Class<?> subject) {
		Annotation annotation = subject.getAnnotation(SuiteClasses.class) ;
		return (SuiteClasses)annotation ;
	}	
}
