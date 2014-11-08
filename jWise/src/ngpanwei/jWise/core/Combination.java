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

/**
 * A combination of values. 
 * @author panwei
 *
 */
public class Combination {
	public static final String SEPARATOR = "|";
	public static final String EMPTY = "_";
	/**
	 * The values.
	 * If the value[i] == null, it is not filled.
	 */
	public String[] value;

	/**
	 * Constructor.
	 * @param size
	 */
	public Combination(int size) {
		value = new String[size];
		for (int i = 0; i < size; i++)
			value[i] = null;
	}
	/**
	 * Create a combination using variable arguments.s
	 * @param values
	 * @return
	 */
	public static Combination create(String ... values) {
		Combination combination = new Combination(values.length) ;
		for(int i=0;i<values.length;i++) {
			combination.value[i] = values[i] ;
		}
		return combination ;
	}
	
	/**
	 * Determine if the combination is all blank.
	 * @return
	 */
	public boolean isBlank() {
		for (int i = 0; i < value.length ; i++) {
			if(value[i] != null)
				return false ;
		}
		return true ;
	}
	/**
	 * Get a key for the combination.
	 * Key is a concatenation of values.
	 * @return
	 */
	public String getKey() {
		String key = "";
		for (int i = 0; i < value.length; i++) {
			if (value[i] == null) {
				key = key + EMPTY;
			} else {
				key = key + value[i];
			}
			if (i < value.length - 1)
				key = key + SEPARATOR;
		}
		return key;
	}
	/**
	 * Determine if this combination is filled/completed.
	 * A filled combination has all non-null values.
	 * @return
	 */
	public boolean isFilled() {
		for (int i = 0; i < value.length; i++) {
			if (value[i] == null)
				return false;
		}
		return true;
	}
	/**
	 * Merge a combination into this combination to produce
	 * another combination
	 * @param operand the combination to be merged with this combination.
	 * @return null if merge has conflicts.
	 */
	public Combination merge(Combination operand) {
		Combination result = new Combination(value.length);
		for (int i = 0; i < value.length; i++) {
			result.value[i] = value[i];
			if (operand.value[i] != null) {
				if (result.value[i] == null) {
					result.value[i] = operand.value[i];
				} else {
					if (result.value[i].equals(operand.value[i]) == false)
						return null;
				}
			}
		}
		return result;
	}
	/**
	 * Find the difference between given combination with this combination.
	 * @param operand
	 * @return null if difference is not valid.
	 */
	public Combination diff(Combination operand) {
		Combination result = new Combination(value.length);
		for (int i = 0; i < value.length; i++) {
			result.value[i] = value[i];
			if (operand.value[i] != null) {
				if (result.value[i] == null) {
					result.value[i] = operand.value[i];
				} else {
					if (result.value[i].equals(operand.value[i]))
						result.value[i] = null;
					else
						return null;
				}
			}
		}
		return result;
	}

}
