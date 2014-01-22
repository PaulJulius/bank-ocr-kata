package com.pauljulius.bankocr;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for reading all the lines from a LineNumberReader.
 * 
 */
public class LineNumberReaderHelper {

	public static List<String> readAll(LineNumberReader reader) throws IOException {
		return readAll(reader, 27);
	}
	
	public static List<String> readAll(LineNumberReader reader, int lineLength) throws IOException {
        List<String> all = new ArrayList<String>();
        String nextLine = reader.readLine(); 
        while(nextLine != null) {
            validateLineLength(reader, nextLine, lineLength);
                
            all.add(nextLine);
            nextLine = reader.readLine();
        }
        
        return all;
    }

	private static void validateLineLength(LineNumberReader reader, String nextLine, int lineLength) {
		if (nextLine.length() < lineLength) {
			throw new IllegalArgumentException("Line number " + reader.getLineNumber() + " contains only " + nextLine.length() + " characters, but it should contain " + lineLength + ".");
		}
	}
}
