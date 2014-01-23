package com.pauljulius.bankocr;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the Bank OCR application. It offers a command line that accepts an input file, processes all the
 * account numbers, and optionally writes them to an output file.
 */
public class BankOCR {

	static final String ZERO =
	                " _ " +
                    "| |" +
                    "|_|";
    static final String ONE = 
                    "   " +
                    "  |" +
                    "  |";
    private static final String TWO =
                    " _ " +
                    " _|" +
                    "|_ ";
    private static final String THREE = 
                    " _ " +
                    " _|" +
                    " _|";
    private static final String FOUR =
                    "   " +
                    "|_|" +
                    "  |";
    private static final String FIVE = 
                    " _ " +
                    "|_ " +
                    " _|";
    private static final String SIX = 
                    " _ " +
                    "|_ " +
                    "|_|";
    private static final String SEVEN =
                    " _ " +
                    "  |" +
                    "  |";
    private static final String EIGHT = 
                    " _ " +
                    "|_|" +
                    "|_|";
    private static final String NINE = 
                    " _ " +
                    "|_|" +
                    " _|";
    
    public static void main(String[] args) throws IOException {
        process(new LineNumberReader(new FileReader(args[0])), new PrintWriter(System.out));
	}

    public static void process(LineNumberReader reader, PrintWriter printWriter) throws IOException {
        //While 4 lines available
        List<String> lines = null;
        while ((lines = readFourLines(reader)) != null) {
            //Convert to account number
            String nextAccountNumber = convert(lines);
            //Write out account number
            printWriter.println(nextAccountNumber);
        }
    }

    public static List<String> readFourLines(LineNumberReader reader) throws IOException {
        List<String> lines = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            
            String readLine = reader.readLine();
            if (readLine == null && i == 0) return null;
            if (readLine == null && i > 0) throw new IOException("Did not read a full group of 4 lines. Only found " + (i+1) + " lines. Last line number read: " + reader.getLineNumber());
            lines.add(readLine);
        }
        return lines;
    }

    public static String convert(List<String> lines) {
        String account = "";
        //while the lines have more display digits to read
        while(lines.get(0).length() > 0) {
            //pull off the next display digit
            String displayDigit = nextDisplayDigit(lines);
            //convert it to a number
            int num = intFor(displayDigit);
            //append it to the full account number
            account += num;
            
        }
        return account;
    }

    /**
     * Has the side effect of removing the digit from the lines, as well.
     */
    public static String nextDisplayDigit(List<String> lines) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String next = lines.get(i);
            buf.append(next.substring(0, 3));
            lines.set(i, next.substring(3));
        }
        return buf.toString();
    }

    public static int intFor(String display) {
        switch (display) { 
            case ZERO: return 0;
            case ONE: return 1;
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
        }
        throw new IllegalArgumentException();
    }


}
