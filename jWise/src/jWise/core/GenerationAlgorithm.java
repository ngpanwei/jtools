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

package jWise.core;

import java.util.Vector;

/**
 * Abstract class for the algorithm.
 * @author panwei
 *
 */
public abstract class GenerationAlgorithm {
	/**
	 * Main method to generate combinations to be overwritten by child classes.
	 * @param generator
	 * @param nwise
	 */
	public abstract void generate(Generator generator, int nwise,
			Vector<Combination> requiredList) ;
	/**
	 * Reference to the generator.
	 */
	protected Generator	_generator;
	/**
	 * Log a message.
	 * @param message
	 */
	protected void log(String message) {
		_generator.log(message) ;
	}
	/**
	 * Get the domain from the generator.
	 * @return
	 */
	protected Domain domain() {
		return _generator.domain();
	}
	/**
	 * Get the result output.
	 * @return
	 */
	protected CombinationTable result() {
		return _generator.result();
	}

}
