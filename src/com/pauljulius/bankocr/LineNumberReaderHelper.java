package com.pauljulius.bankocr;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class LineNumberReaderHelper {

	public static List<String> readAll(LineNumberReader reader) throws IOException {
		List<String> all = new ArrayList<String>();
		String nextLine = reader.readLine(); 
		while(nextLine != null) {
			validateLineLength(reader, nextLine);
				
			all.add(nextLine);
			nextLine = reader.readLine();
		}
		
		return all;
	}

	private static void validateLineLength(LineNumberReader reader, String nextLine) {
		if (nextLine.length() < 27) {
			throw new IllegalArgumentException("Line number " + reader.getLineNumber() + " contains only " + nextLine.length() + " characters, but it should contain 27.");
		}
	}
}
