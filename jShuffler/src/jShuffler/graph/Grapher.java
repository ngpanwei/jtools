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
package jShuffler.graph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * This class generates an execution graph in GraphViz format and saves the graph
 * as a file.
 * @author panwei
 */
public class Grapher {

	/**
	 * Save the graph as a file.
	 * @param fileName
	 */
	public void save(String fileName) throws GraphException {
		try {
			File theFile = new File(fileName);
	        Writer out = new OutputStreamWriter(new FileOutputStream(theFile), "UTF-8");
	        String theHtml = "hello" ;
	        out.write(theHtml);
	        out.close();
		} catch (IOException e) {
			throw new GraphException(e.getMessage());
		}
	}
	protected String getGraphText() {
		String text = "" ;
		text += "digraph execution {\n" ;
		text += "\trankdir=LR"  ;
		text += "A [label = 'A']" ;
		text += "B [label = 'B']" ;
		text += "\trankdir=TB"  ;
		text += "A -> B [label = 'f1' ]" ;
		text += "}" ;
		return text ;
	}
}
