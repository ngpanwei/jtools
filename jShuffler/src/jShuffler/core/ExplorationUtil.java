/** 
  *  Copyright (c) 2011  Ng Pan Wei
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
package jShuffler.core;

import java.util.Random;
/**
 * Some utilities.
 * @author panwei
 */
public class ExplorationUtil {
	/**
	 * Sleep without exception.
	 * @param millis
	 */
	@SuppressWarnings("static-access")
	public static void sleep(int millis) {
		try {
			Thread z = Thread.currentThread() ;
			z.sleep(millis) ;
		} catch (Exception e) {
			System.err.println(Thread.currentThread().getName()+" error "+e.getMessage()) ;
		}
	}
	/**
	 * Some random number generator.
	 * @param range
	 * @return
	 */
	public static int random(int range) {
		Random a = new Random();
		int value = Math.abs(a.nextInt()% range)  ;
		return value ;
	}
}
