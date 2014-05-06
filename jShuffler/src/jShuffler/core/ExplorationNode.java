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

import java.util.Stack;

/**
 * Node to keep track of the visits.
 * @author panwei
 *
 */
public class ExplorationNode {

	/**
	 * Unique name for this execution condition
	 */
	protected String nodeId ;
	/**
	 * Thread id of threads that have visited this node.
	 */
	protected Stack<Integer> visits ;
	/**
	 * Thread id of threads that have visited this node.
	 */
	protected Stack<Integer> unvisited;
	/**
	 * Constructor.
	 * @param nodeIdValue
	 */
	ExplorationNode(String nodeIdValue) {
		nodeId = nodeIdValue ;
		visits = new Stack<Integer>() ;
		unvisited = new Stack<Integer>() ;
	}
	/**
	 * Check if the thread has visited this node.
	 * @param threadId
	 * @return
	 */
	boolean hasVisited(Integer threadId) {
		if(visits.contains(threadId)) {
			return true ;
		} else {
			return false ;
		}
	}
	/**
	 * Determine if the thread has last visited (in the previous test run) this node.
	 * @param thread
	 * @param threads
	 * @return
	 */
	boolean hasLastVisited(Integer threadId) {
		if(visits.empty())
			return false ;
		if(visits.peek()==threadId)
			return true ;
		return false ;
	}
	/**
	 * Visit this node by putting the threadId onto the visits stack.
	 * @param threadId
	 * @param threads
	 */
	void vist(Integer threadId) {
		if(visits.contains(threadId)) {
			return ;
		}
		visits.push(threadId) ;
	}
	/**
	 * Return the number of thread visits from this node.
	 * @return
	 */
	int totalVisits() {
		return visits.size() ;
	}
}
