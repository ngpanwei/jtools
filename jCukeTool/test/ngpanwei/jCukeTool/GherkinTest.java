package ngpanwei.jCukeTool;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class GherkinTest {
	@Test
	public void testGherkin() throws IOException {
		new GherkinReader(new OutputStreamWriter(System.out, "UTF-8"))
				.scanAll(new File("requirements/ngpanwei/jCukeTool/features/Formatter.feature"));
	}
}
