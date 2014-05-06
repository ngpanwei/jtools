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
package jWatcher;

import junit.framework.TestCase;

public class WatcherTest extends TestCase {
	/**
	 * Test to use the native Watcher class.
	 */
	public void testLog() {
		Watcher.log() ;
	}
	/**
	 * Test to use the native Watcher class with a log message.
	 */
	public void testLogMessage() {
		Watcher.log("Here") ;
	}
	/**
	 * Test to use the native Watcher class with a log message.
	 */
	public void testGetSignature() {
		Watcher.log("Getting Signature") ;
		System.err.println("\tSignature is "+Watcher.getSignature()) ;
	}
	/**
	 * Test to use the custom Watcher class with a log message.
	 */
	public void testCustomLog() {
		CustomWatcher.customLog() ;
	}
	
}
