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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CukeFormatter {
	public static void main(String args[]) throws Exception {
		List<File> featureFiles = new ArrayList<File>();
		searchFolders(featureFiles, ".feature");
		for(File featureFile : featureFiles) {
			System.err.println("process : "+featureFile.getCanonicalPath()) ;
			List<String> lines = readLines(featureFile) ;
			List<String> formattedLines = formatLines(lines) ;
			saveFile(featureFile,formattedLines) ;
		}
	}

	private static void saveFile(File file,List<String> lines) throws Exception {
		Writer out = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream(file.getCanonicalFile()), "UTF-8"));
		for(String line : lines) {
			out.write(line+System.getProperty("line.separator"));
		}
	    out.close();
	}
	private static List<String> formatLines(List<String> inputLines) {
		List<String> lines = new ArrayList<String>() ;
		for(String line : inputLines) {
			if(isKeyword(line)) {
				line = System.getProperty("line.separator") + line ;
			}
			lines.add(line) ;
		}
		return lines ;
	}

	private static boolean isKeyword(String line) {
		return line.startsWith("Scenario");
	}
	private static List<String> readLines(File file) throws Exception {
		
		List<String> lines = new ArrayList<String>() ;
		BufferedReader in = new BufferedReader(
				   new InputStreamReader(
		                      new FileInputStream(file), "UTF8"));
		String str;
 		while ((str = (String)in.readLine()) != null) {
			if(str.trim().length()<1) continue ;
			lines.add(str) ;
		}
		in.close();
		return lines ;
	}

	private static void searchFolders(List<File> files, String extension)
			throws Exception {
		File dir = new File("./");
		searchFolders(files, extension, dir);
	}

	private static void searchFolders(List<File> files, String extension,
			File curDir) throws Exception {
		File[] filesInFolder = curDir.listFiles();
		for (File file : filesInFolder) {
//			System.err.println(file.getCanonicalPath()) ;
			if (file.getCanonicalPath().endsWith(extension)) {
//				System.err.println("****"+file.getCanonicalPath()) ;
				files.add(file) ;
			}
			if (file.isDirectory()) {
				searchFolders(files, extension, file);
			}
		}
	}
}
