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

package jsupervisor.verifiers.value;

import java.util.Vector;

/**
 * Factory class for creating value verifiers.
 * @author panwei
 */
public class Value {
	/**
	 * Default value verifier.
	 */
	protected static ValueVerifier _defaultValueVerifier ;
	
	public static ValueVerifier Any ;
	public static ValueVerifier Exactly ;
	public static ValueVerifier IsNotNull ;
	public static ValueVerifier IsNull;
	/**
	 * Static initialization.
	 */
	static {
		_defaultValueVerifier = new ExactValue();
		Any = new AnyValue() ;
		Exactly = new ExactValue() ;
		IsNotNull = new IsNonNullValue() ;
		IsNull = new IsNullValue() ;
	}

	/**
	 * create default verifiers (when client does not specify any)
	 * 
	 * @param args
	 * @return
	 */
	public static Vector<ValueVerifier> getDefault(Object[] args) {
		Vector<ValueVerifier> verifiers = new Vector<ValueVerifier>();
		if (args == null)
			return verifiers;
		for (int i = 0; i < args.length; i++) {
			verifiers.add(_defaultValueVerifier);
		}
		return verifiers;
	}
	/**
	 * Get default verification (when client does not specify any).
	 * @return
	 */
	public static ValueVerifier getDefault() {
		return _defaultValueVerifier ;
	}
}
