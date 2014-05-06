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

import jShuffler.core.Exploration;
import jShuffler.core.ExplorationUtil;

/**
 * Aspect to inject dealing points into player's turn.
 * Each player turn is a step in the execution sequence.
 * A player can proceed with a turn if it is dealt successfully. 
 * @author panwei
 *
 */
public aspect BasicShufflingAspect {
	/**
	 * All designated visiting point are rendezvous points for
	 * exploration shuffling.
	 */
	pointcut visitingPoint() : withincode(void doProcedure*()) ;
	
	/**
	 * Before each visiting point, check if the thread can proceed.
	 * Otherwise, sleep a bit and wait for next turn.
	 */
	before() : visitingPoint() {
		String point = thisJoinPoint.getSignature().getName();
		while(Exploration.get().canProceed(point)==false) {
			ExplorationUtil.sleep(WaitingTime) ;
		}
	}
	/**
	 * Time to wait before re-trying when thread is not allow to pass.
	 */
	public static int WaitingTime = 500;
}
