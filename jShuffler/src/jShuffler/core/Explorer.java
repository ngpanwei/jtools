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

/**
 * Execution for exploring all possible execution paths.
 * Each execution run gets a number of threads to execute in some particular sequence.
 * @author panwei
 *
 */
public abstract class Explorer {
	/**
	 * nnumber of active threads in a run.
	 */
	protected int threadCount = 0 ;
	/**
	 * Explore all possible execution sequences.
	 * Each game run produces one execution sequence.
	 * @param totalThreads
	 * @return the total number of game runs.
	 */
	public int exploreAll(int totalThreads) {
		int run = 0 ;
		beforeAllExploration(totalThreads) ;
		while(true) {
			if(explore(run,totalThreads)==false)  {
				afterAllExploration(totalThreads) ;
				return run ;
			}
			run++ ;
		}
	}
	/**
	 * Play one single game run. 
	 * Each player join the game as a thread.
	 * @param totalThreads
	 * @return
	 */
	public boolean explore(int run, int totalThreads) {
		beforeEachExploration(run,totalThreads) ;
    	threadCount = 0 ;
    	if(Exploration.get().start()==false) {
    		return false;
    	}
    	for(int i=0;i<totalThreads;i++) {
    		threadCount++ ;
    		final int threadId = i + 1 ;
    		Thread thread = new Thread() {
    			public void run() {
    				ExplorationUtil.sleep(100*threadId);
    				explore(threadId);
    				threadCount -- ;
    	    		Exploration.get().removeThread(threadId,Thread.currentThread());
    			}
    		};
    		Exploration.get().addThread(threadId,thread);
    		thread.start();
    	}
    	while(threadCount>0) {
			ExplorationUtil.sleep(100);
    	}
    	afterEachExploration(run,totalThreads) ;
    	return true ;
	}
	/**
	 * This is a execution which each thread tries to go through.
	 * @param threadId
	 */
	public abstract void explore(final int threadId) ;
	/**
	 * This is executed before each exploration run.
	 * You can do some test initialisation here.
	 * @param run
	 */
	public void beforeEachExploration(int run,int totalThreads) {}
	/**
	 * This is executed at the end of each exploration run.
	 * You can do some clean up and verification here.
	 * @param run
	 */
	public void afterEachExploration(int run,int totalThreads) {}
	/**
	 * This is executed before all exploration runs.
	 */
	public void beforeAllExploration(int totalThreads) {}
	/**
	 * This is executed after all exploration runs.
	 */
	public void afterAllExploration(int totalThreads) {}
}
