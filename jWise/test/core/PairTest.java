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

package core;

import jWise.algorithm.basic.BasicAlgorithm;
import jWise.core.Combination;
import jWise.core.CombinationTable;
import jWise.core.Dimension;
import jWise.core.Domain;
import jWise.core.Generator;

import java.util.Vector;

import junit.framework.TestCase;

import org.junit.Test;

/***
 * Test chttps://github.com/ngpanwei/examples.gitase to test the core generator algorithm.
 * 
 * @author panwei
 * 
 */
public class PairTest extends TestCase {
	public PairTest() {
	}

	/***
	 * Test case #1 to generate the combinations.
	 */
	@Test
	public void testGeneratePair() {
		Domain domain = new Domain();
		String a[] = { "A1", "A2", "A3" };
		String b[] = { "B1", "B2", "B3" };
		String c[] = { "C1", "C2" };
		domain.add(new Dimension("A", a));
		domain.add(new Dimension("B", b));
		domain.add(new Dimension("C", c));
		executePairGeneration(domain);
	}
	/***
	 * Exactly the same as Test case #1, but using a simpler API
	 * with the help of variable arguments.
	 */
	@Test
	public void testGeneratePair_usingVariableArg() {
		Domain domain = new Domain();
		domain.add("A", "A1", "A2", "A3");
		domain.add("B", "B1", "B2", "B3");
		domain.add("C", "C1", "C2");
		executePairGeneration(domain);
	}
	/***
	 * Test Case #2 that generates pair wise combinations that 
	 * include some required combinations.
	 */
	@Test
	public void testGeneratePair2D() {
		Domain domain = new Domain();
		domain.add("A", "A1","A2");
		domain.add("B", "B1","B2");
		domain.add("C", "C1", "C2");
		Vector<Combination> requiredList = new Vector<Combination>() ;
		requiredList.add(Combination.create("A2","B2","C2")) ;
		requiredList.add(Combination.create("A1","B2","C1")) ;
		executePairGeneration(domain,requiredList);
	}
	/**
	 * Generates pairwise combinations in a domain that includes some required
	 * combinations.
	 * @param domain variable domains.
	 * @param requiredList required combinations of values.
	 * @return Combinations
	 */
	private CombinationTable executePairGeneration(Domain domain,Vector<Combination> requiredList) {
		Generator generator = new Generator(domain);
		generator.verbose = false;
		generator.generate(new BasicAlgorithm(5), 2, requiredList);
		CombinationTable combinations = generator.result();
		assertEquals(generator.span(), combinations.span());
		for(Dimension dim :domain.getDimensions()) {
			System.err.print(Combination.SEPARATOR+ dim.name) ;
			
		}
		System.err.println(Combination.SEPARATOR) ;
		printCombinations(generator);
		return combinations ;
	}

	private void printCombinations(Generator generator) {
		for (Combination entry : generator.result().combinations()) {
			System.err.println(Combination.SEPARATOR
						+ entry.getKey() + Combination.SEPARATOR);
		}
		System.err.println(generator.result().combinations().size()
				+ " combinations.");
	}
	/**
	 * Wrapper for helper function that has null required combinations.
	 * @param domain
	 * @return
	 */
	private CombinationTable executePairGeneration(Domain domain) {
		return executePairGeneration(domain,null) ;
	}
}
