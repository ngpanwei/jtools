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
