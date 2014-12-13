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
package ngpanwei.jCukeTool;

import gherkin.formatter.Formatter;
import gherkin.formatter.PrettyFormatter;
import gherkin.parser.Parser;
import gherkin.util.FixJava;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.Test;

public class GherkinReader {
	
	private FileFilter featureFilter = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory() || file.getName().endsWith(".feature");

		}

	};
	private Parser parser;
	private final Writer out;

	public GherkinReader(final Writer out) {
		this.out = out;
		final Formatter formatter = new PrettyFormatter(out, true, true);
		parser = new Parser(formatter);
	}

	void scanAll(File file) throws IOException {
		walk(file);
		out.append('\n');
		out.close();
	}

	private void walk(File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles(featureFilter)) {
				walk(child);
			}
		} else {
			parse(file);
		}
	}

	private void parse(File file) {
		try {
			String input = FixJava.readReader(new FileReader(file));
			parser.parse(input, file.getPath(), 0);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
}
