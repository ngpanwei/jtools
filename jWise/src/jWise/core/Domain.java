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
 * The problem domain which we want to cover.
 * It has many dimensions, each having several possible values.
 * 
 * @author panwei
 */
public class Domain {
	/**
	 * List of dimensions.
	 */
	protected Vector<Dimension> dimensions ;
	/**
	 * Constructor.
	 */
	public Domain() {
		dimensions = new Vector<Dimension>() ;
	}
	/**
	 * add a dimension.
	 * @param newDimension
	 */
	public void add(Dimension newDimension) {
		dimensions.add(newDimension) ;
	}
	/**
	 * add a dimension easily via variable string arguments.
	 * @param dimName
	 * @param values
	 */
	public void add(String dimName, String ... values) {
		Dimension dim = new Dimension(dimName, values) ;
		dimensions.add(dim) ;
	}
	/**
	 * return the number of dimension of the domain.
	 * @return
	 */
	public int size() {
		return dimensions.size();
	}
	/**
	 * Get a dimension via its index.
	 * @param index
	 * @return
	 */
	public Dimension get(int index) {
		return dimensions.get(index) ;
	}
	/**
	 * Get dimensions.
	 * @return
	 */
	public Vector<Dimension> getDimensions() {
		return dimensions ;
	}
}
