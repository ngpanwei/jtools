/** 
  *  Copyright (c) 2014  Ng Pan Wei
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
package ngpanwei.jCukeTool.formatter;

import ngpanwei.jCukeTool.model.Element;

public class CSVFormatter extends Formatter {
	public String printHeader(Element element) {
		String str = "type" + ","
				+ "name" + ","
				+ "passed" + ","
				+ "failed" + ","
				+ "skipped" + ","
				+ "undefined" + ","
				+ "total" + System.lineSeparator() ;
		return str ;
	}
	public String printLine(Element element) {
		String str = element.keyword + ","
				+ element.name + ","
				+ element.counter.map.get("passed") + ","
				+ element.counter.map.get("failed") + ","
				+ element.counter.map.get("skipped") + ","
				+ element.counter.map.get("undefined") + ","
				+ element.counter.map.get("total") 
				+ System.lineSeparator() ;
		return str ;
	}
	public String printFooter(Element element) {
		return "" ;
	}	
}
