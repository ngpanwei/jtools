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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is responsible for "pausing" or allowing threads to proceed
 * through each monitored exploration point.
 * @author panwei
 *
 */
public class Exploration {
	/**
	 * States
	 */
	private static final String STATE_START = "@:_";
	private static final String STATE_END = "@:!";
	/**
	 * Just to keep track of the time in this exploration run.
	 * This is just for dumping (printing).
	 */
	public static long time = 1;
	/**
	 * Flag to indicate if we should dump the exploration state.
	 */
	public static boolean dumpEnabled = false ;
	/**
	 * Map containing the state of the thread
	 */
	protected List<Thread> waitingThreads ;
	/**
	 * Map of all threads
	 */
	protected HashMap<Thread,Integer> threads ;
	/**
	 * Map of all threads
	 */
	protected HashMap<Thread,String> threadState ;
	/**
	 * Calculator.
	 */
	protected ExplorationShuffler shuffler ;
	/**
	 * Singleton.
	 */
	protected static Exploration singleton = null ;
	/**
	 * Constructor.
	 */
	public Exploration() {
		waitingThreads = new ArrayList<Thread>();
		threads = new HashMap<Thread,Integer>() ;
		threadState = new HashMap<Thread,String>() ;
		shuffler = new ExplorationShuffler() ;
	}
	/**
	 * Attempt to get through an execution point.
	 * @return
	 */
	public boolean canProceed(String point) {
		synchronized(this) {
			Thread currentThread = Thread.currentThread() ;
			String pointName = shuffler.getPointName(point,threads) ;
			// force all the threads to wait at this point
			if(!waitingThreads.contains(currentThread)) {
				waitingThreads.add(currentThread) ;
				threadState.put(currentThread, "@:"+pointName) ;
				dumpGameState(++time,pointName) ;
			}
			/**
			 * All threads rendezvous here?
			 * TODO: handle case when threads are blocked.
			 */
			if(waitingThreads.size()<threads.size()) {
				return false ;
			}
			/**
			 * Determine if this thread should proceed.
			 */
			String nodeId = shuffler.getNodeId(waitingThreads, threadState) ;
			Integer currentThreadId = threads.get(currentThread) ;
			boolean shouldVisit = false ;
			if(shuffler.shouldVisit(nodeId,currentThreadId,threads)) {
				shouldVisit = true ;
			}
			/**
			 * If there is only one thread, allowed it to proceed regardless.
			 * TODO: handle case when some threads are blocked.
			 */
			if(threads.size()<=1) {
				shouldVisit = true ;
			}
			/**
			 * Visit the node
			 */
			if(shouldVisit==true) {
				waitingThreads.remove(Thread.currentThread()) ;
				threadState.put(Thread.currentThread(), "~:"+pointName) ;
				dumpGameState(time,pointName) ;
				shuffler.visitNode(nodeId,currentThreadId,threads) ;
			}
			return shouldVisit ;
		}
	}
	/**
	 * add a thread to the exploration when exploration starts.
	 * @param id
	 * @param thread
	 */
	public void addThread(int id,Thread thread) {
		synchronized(this) {
			threads.put(thread,id);
			threadState.put(Thread.currentThread(), STATE_START) ;
		}
	}
	/**
	 * Remove thread from the exploration, after this thread is done.
	 * @param id
	 * @param thread
	 */
	public void removeThread(int id,Thread thread) {
		synchronized(this) {
			threads.remove(thread);
			threadState.put(Thread.currentThread(), STATE_END) ;
		}
	}
	public static int getThreadId() {
		return get().threads.get(Thread.currentThread()) ;
	}
	/**
	 * Clear all the data in preparation of a test run.
	 */
	public boolean start() {
		if(shuffler.start()==false) {
			return false ;
		}
		time = 1 ;
		threads.clear() ;
		threadState.clear();
		waitingThreads.clear();
		return true ;
	}
	/**
	 * Get the singleton instance.
	 * @return
	 */
	public static Exploration get() {
		if(singleton==null)
			singleton = new Exploration() ;
		return singleton ;
	}
	protected void dumpGameState(long time,String point) {
		if(dumpEnabled==false)
			return ;
		try {
			System.err.print(time+"\t"+point+"\t");
			System.err.print(waitingThreads.size()+"\t");
			for(Thread thread : waitingThreads) {
				if(thread==null) {
					System.err.print("????\t");
				} else {
					String state = threadState.get(thread);
					System.err.print(state+"\t");
				}
			}
			System.err.println();
		} catch(NullPointerException ignore) {
			System.err.println();
		}
	}

}
