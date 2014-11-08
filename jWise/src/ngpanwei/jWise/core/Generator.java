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

import java.util.Vector;


/**
 * Combination generation algorithm to generate N-wise.
 * Currently support only pair-wise.
 * @author panwei
 *
 */
public class Generator {
	/**
	 * Domain from which combinations will be generated.
	 */
	protected Domain _domain;
	/**
	 * Combinations generated.
	 */
	protected CombinationTable _result;
	/**
	 * Verbosity.
	 */
	public boolean verbose = true;
	/**
	 * Constructor ;
	 * @param theDomain
	 */
	public Generator(Domain theDomain) {
		_domain = theDomain;
		_result = new CombinationTable();
	}
	/**
	 * Delegate generation to algorithm.
	 * @param nwise
	 */
	public void generate(GenerationAlgorithm algorithm,int nwise,
			Vector<Combination> requiredList) {
		algorithm.generate(this,nwise,requiredList) ;
	}
	/**
	 * Get the domain.
	 * @return
	 */
	public Domain domain() {
		return _domain ;
	}
	/**
	 * Get the result.
	 * @return
	 */
	public CombinationTable result() {
		return _result;
	}
	/**
	 * log messages.
	 * @param message
	 */
	public void log(String message) {
		if (verbose == true)
			System.err.println(message);
	}
	/**
	 * Compute the span of the domain.
	 * @return
	 */
	public int span() {
       int size = 0;
       for (int i = 0; i < _domain.size(); i++) {
           for (int j = i+1; j < _domain.size(); j++) {
        	   size += _domain.get(i).value.length
        	   			* _domain.get(j).value.length ;
           }
       }
	   return size;
	}

}
