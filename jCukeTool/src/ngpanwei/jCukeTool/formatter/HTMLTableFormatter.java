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

public class HTMLTableFormatter extends Formatter {
	public String printHeader(Element element) {
		String str = "<table class='cuke-table'><tr>"
				   + "<td>type</td>"
				   + "<td>name</td>"
				   + "<td>passed</td>"
				   + "<td>failed</td>"
				   + "<td>skipped</td>"
				   + "<td>undefined</td>"
				   + "<td>total</td>" 
				   + "</tr>"
				   + System.lineSeparator() ;
		return str ;
	}
	public String printLine(Element element) {
		String str = "<tr>"
				   + "<td>" + element.keyword + "</td>"
				   + "<td>" + element.name + "</td>"
				   + "<td>" + element.counter.map.get("passed") + "</td>"
				   + "<td>" + element.counter.map.get("failed") + "</td>"
				   + "<td>" + element.counter.map.get("skipped") + "</td>"
				   + "<td>" + element.counter.map.get("undefined") + "</td>"
				   + "<td>" + element.counter.map.get("total") + "</td>"
				   + "</tr>"
				   + System.lineSeparator() ;
		return str ;
	}
	public String printFooter(Element element) {
		return "</tr></table>" 
			  + System.lineSeparator() ;
	}
}
