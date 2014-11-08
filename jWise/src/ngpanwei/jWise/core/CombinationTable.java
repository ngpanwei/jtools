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

package ngpanwei.jWise.core;

import java.util.HashMap;
import java.util.Vector;

/**
 * Combination Table.
 * @author panwei
 *
 */
public class CombinationTable {
	/**
	 * List of combinations.
	 */
	Vector<Combination> _combinations;
	/**
	 * Constructor.
	 */
	public CombinationTable() {
		_combinations = new Vector<Combination>() ;
	}
	/**
	 * Add a combination.
	 * @param combination
	 */
	public void add(Combination combination) {
		_combinations.add(combination) ;
	}
	/**
	 * The size of the table.
	 * @return
	 */
	public int size() {
		return _combinations.size() ;
	}
	/**
	 * Get the combinations.
	 * @return
	 */
	public Vector<Combination> combinations() {
		return _combinations ;
	}
	/**
	 * Get the depth of the combinations.
	 * @return
	 */
	public int breadth() {
		Combination combination = _combinations.get(0) ;
		if(combination==null)
			return -1 ;
		return combination.value.length ;
	}
	/**
	 * Get the number of N-Pairs spanned by this set of combinations.
	 * @return
	 */
	public int span() {
		int depth = breadth() ;
		if(depth<0)
			return -1 ;
		HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
		for (int i = 0; i < depth; i++) {
			for (int j = i + 1; j < depth; j++) {
				for (Combination generatedEntry : _combinations) {
					if (generatedEntry.value[i] != null
							&& generatedEntry.value[j] != null) {
						Combination entry = new Combination(depth);
						entry.value[i] = generatedEntry.value[i];
						entry.value[j] = generatedEntry.value[j];
						String key = entry.getKey();
						tempMap.put(key, 1);
					}
				}
			}
		}
		return tempMap.size();
	}

}
