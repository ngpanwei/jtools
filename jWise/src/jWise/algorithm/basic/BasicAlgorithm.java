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

package jWise.algorithm.basic;

import jWise.core.GenerationAlgorithm;
import jWise.core.Combination;
import jWise.core.Dimension;
import jWise.core.Domain;
import jWise.core.Generator;

import java.util.HashMap;
import java.util.Vector;

/**
 * Basic Algorithm to generate combinations.
 * @author panwei
 */
public class BasicAlgorithm extends GenerationAlgorithm {
	/**
	 * Flag value to indicate that a combination pair has been generated.
	 */
	public static final Integer COMPLETED = 1;
	/**
	 * Flag value to indicate that a combination pair has NOT been generated.
	 */
	public static final Integer PENDING = 0;
	/**
	 * Map of candidate combination pairs.
	 */
	protected HashMap<String, Integer> _combinationMap;
	/**
	 * List of unprocessed combination pairs.
	 */
	protected Vector<Combination> _combinationQ;
	/**
	 * parameter to tune the selection of unprocessed combination pairs.
	 */
	int _jump = 3;
	/**
	 * Constructor.
	 */
	public BasicAlgorithm(int jump) {
		_combinationMap = new HashMap<String, Integer>();
		_combinationQ = new Vector<Combination>();
		_jump = jump ;
	}
	/**
	 * Main generation algorithm without required combinations.
	 */
	public void generate(Generator generator, int nwise) {
		Vector<Combination> requiredCombinations = null ;
		generate(generator,nwise,requiredCombinations) ;
	}
	/**
	 * Main generation algorithm.
	 * @param requiredCombinations these are the required cases 
	 * which must be tested. Algorithm will add test cases until nwise is achieved.
	 */
	public void generate(Generator generator, int nwise, 
			Vector<Combination> requiredCombinations) {
		_generator = generator ;
		generatePartialCombinations(nwise);
		if(requiredCombinations!=null) {
			for(Combination combination : requiredCombinations) {
				markCombinations(combination);
				result().add(combination);
			}
		}
		while (_combinationQ.size() > 0) {
			Combination entry = buildCombination(nwise);
		}
	}
	/**
	 * Generate candidate partial combinations.
	 * @param nwise
	 */
	protected void generatePartialCombinations(int nwise) {
		int size = _generator.domain().size() ;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				generatePairs(i, j, nwise);
			}
		}
	}
	/**
	 * Generate partial combination for parameters.
	 * @param i
	 * @param j
	 * @param nwise
	 */
	protected void generatePairs(int i, int j, int nwise) {
		Domain domain = _generator.domain() ;
		Dimension p1 = domain.get(i);
		Dimension p2 = domain.get(j);
		for (String v1 : p1.value) {
			for (String v2 : p2.value) {
				Combination entry = new Combination(domain.size());
				entry.value[i] = v1;
				entry.value[j] = v2;
				String key = entry.getKey();
				_combinationMap.put(key, PENDING);
				_combinationQ.add(entry);
			}
		}
	}
	/***
	 * Build a single combination from what is available in the queue 
	 * that increases nwise coverage.
	 * @param nwise
	 * @return
	 */
	protected Combination buildCombination(int nwise) {
		log("Combination::buildCombination --- ") ;
		int offset = -_jump;
		Combination curCombination = new Combination(domain().size());

		Vector<Combination> tempQueue = new Vector<Combination>();
		while (curCombination.isFilled() == false && _combinationQ.size() > 0) {
			offset = (offset + _jump) % _combinationQ.size();
			Combination temp = _combinationQ.remove(offset);
			String key = temp.getKey();
			log(" - trying: " + key);
			Integer status = _combinationMap.get(key);
			if (status == COMPLETED) {
				log(" - skipping: " + key);
				continue;
			}
			Combination mergedCombination = curCombination.merge(temp);
			if (mergedCombination == null) {
				tempQueue.add(temp);
				log(" - postponing: " + key);
				continue;
			}
			curCombination = mergedCombination;
			// log("marking: "+curCombination.getKey()) ;
			// markCombinations(curCombination);
		}
		// put postponed partial combinations back into processing queue.
		for (Combination temp : tempQueue) {
			_combinationQ.add(temp);
		}
		log("marking: "+curCombination.getKey()) ;
		if(curCombination.isBlank()==false) {
			markCombinations(curCombination);
			result().add(curCombination);
			log("Progress result:" + _generator.result().size() + " queue:"
					+ _combinationQ.size() + " -- " + curCombination.getKey());
			return curCombination;
		}
		return null ;
	}
	/**
	 * Mark partial combinations that have been used.
	 * @param combination
	 */
	public void markCombinations(Combination combination) {
		int size = domain().size() ;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (combination.value[i] != null && combination.value[j] != null) {
					Combination pair = new Combination(size);
					pair.value[i] = combination.value[i];
					pair.value[j] = combination.value[j];
					String key = pair.getKey();
					Integer status = _combinationMap.get(key);
					if (status != COMPLETED) {
						log(" - clearing: " + key);
						_combinationMap.put(key, COMPLETED);
					}
				}
			}
		}
	}
}
