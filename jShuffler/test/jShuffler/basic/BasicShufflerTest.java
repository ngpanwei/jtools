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
package jShuffler.basic;

import jShuffler.core.Explorer;
import jShuffler.graph.Grapher;

import org.junit.Test;

/**
 * Test case to test an application under a multi-threaded execution environment.
 * @author panwei
 */
public class BasicShufflerTest {
	/**
	 * Number of threads to be used.
	 */
	private static final int NUM_THREADS = 2;
    @Test
	public void testProcedureConcurrently() {
    	/**
    	 * Create a new game with a player execution track.
    	 */
		Explorer explorer = new Explorer() {
			public void explore(final int playerId) {
				Target target = new Target(playerId) ;
				target.doProcedure(playerId) ;
			}		
			public void beforeEachExploration(int run,int totalThreads) {
				System.err.println("Run no:"+(run+1)) ;
			}
		} ;
		int runs = explorer.exploreAll(NUM_THREADS);
		System.err.println("Total runs = "+runs) ;
    }
}
