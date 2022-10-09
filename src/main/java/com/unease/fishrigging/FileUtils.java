package com.unease.fishrigging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

/**
 * @author ScribbleLP
 */
public class FileUtils {

	// Will try to revamp how the file works to try to make it cleaner later...
	public static void writeTo(StringBuilder output, File file, String logmessage) {
		try {
			Files.write(output.toString().getBytes(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readFrom(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> out = new ArrayList<String>();
		String s;
		while ((s=reader.readLine()) != null) {
			out.add(s);
		}
		reader.close();
		return out;
	}
	
	public static int countLines(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while(reader.readLine() != null) {
			lines++;
			reader.close();
		}
		return lines;
	}
	
}
