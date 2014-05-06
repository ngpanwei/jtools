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

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * This class determines the execution sequence of the threads.
 * @author panwei
 */
public class ExplorationShuffler {

	/**
	 * This is the list of all nodes in the possible execution path.
	 */
	protected HashMap<String,ExplorationNode> explorationNodes ;
	/**
	 * This is the current execution path.
	 */
	protected Stack<ExplorationNode> explorationPath ;
	/**
	 * Queue of nodes to attempt alternate paths.
	 * When queue is empty, all paths have been explored.
	 */
	protected Stack<ExplorationNode> explorationQueue ;
	/**
	 * Constructor.
	 */
	ExplorationShuffler() {
		explorationNodes = new HashMap<String,ExplorationNode>() ;
		explorationPath = new Stack<ExplorationNode>() ;
		explorationQueue = new Stack<ExplorationNode>() ;
	}
	/**
	 * Set the start conditions for this run.
	 */
	boolean start() {
		boolean result = needExploration() ;
		explorationPath.clear();
		return result ;
	}
	/**
	 * Determine the decision node where path exploration should take place
	 * in this execution run.
	 * @param maxThreads
	 * @return
	 */
	boolean needExploration() {
		/**
		 * If there are no nodes in memory, this is first time explorer is invoked.
		 * So let it pass.
		 */
		if(explorationNodes.size()<1) {
			return true ;
		}
		/**
		 * Take a node to be explored and perturb it.
		 */
		while(explorationQueue.empty()==false) {
			ExplorationNode node = explorationQueue.pop();
			if(node.unvisited.empty()==false) {
				Integer threadId = node.unvisited.pop();
				node.visits.push(threadId) ;
				if(node.unvisited.empty()==false) {
					explorationQueue.push(node) ;
				}
				return true ;
			}
		}
		return false ;
	}
	/**
	 * Determine if the thread should proceed through the node.
	 * @param nodeId
	 * @param thread
	 * @param threads
	 * @return
	 */
	boolean shouldVisit(String nodeId, Integer threadId, HashMap<Thread,Integer> threads) {
		ExplorationNode node = getNode(nodeId,threadId,threads);
		return node.hasLastVisited(threadId) ;
	}
	public void visitNode(String nodeId, Integer threadId,HashMap<Thread,Integer> threads) {
		ExplorationNode node = getNode(nodeId,threadId,threads);
		node.vist(threadId) ;
		if(explorationPath.contains(threadId)==false) {
			explorationPath.push(node) ;
		}
	}
	private ExplorationNode getNode(String nodeId,Integer threadId,HashMap<Thread,Integer> threads) {
		ExplorationNode node;
		if(explorationNodes.containsKey(nodeId)==false) {
			node = new ExplorationNode(nodeId) ;
			node.visits.push(threadId) ;
			explorationNodes.put(nodeId,node) ;
			for(Thread thread : threads.keySet()) {
				Integer theThreadId = threads.get(thread) ;
				if(theThreadId!=threadId) {
					node.unvisited.push(theThreadId) ;
				}
			}
			explorationQueue.push(node) ;
		} else {
			node = explorationNodes.get(nodeId);
		}
		return node;
	}
	public String getPointName(String point,
									HashMap<Thread,Integer> threads) {
		Integer threadId = threads.get(Thread.currentThread()) ;
		String pointName = threadId.toString() + ":" + point ;
		return pointName ;
	}
	/**
	 * Determine the current state of the threads execution.
	 * This is determined by the current point of all the threads.
	 * @param waitingThreads
	 * @param threadState
	 * @return
	 */
	public String getNodeId(List<Thread> waitingThreads,
							HashMap<Thread,String> threadState) {
		String nodeId = "" ;
		for(Thread thread : waitingThreads) {
			String state = threadState.get(thread) ;
			if(nodeId.length()<1) {
				nodeId = state ;
			} else {
				nodeId = nodeId + "," + state ;
			}
		}
		return nodeId ;
	}
	
}
