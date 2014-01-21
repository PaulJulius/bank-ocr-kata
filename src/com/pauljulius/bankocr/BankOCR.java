package com.pauljulius.bankocr;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

public class BankOCR {
	
    public static void main(String[] args) throws IOException {
		if (args == null || args.length < 1) {
			System.err.println("You must specify the filename to process as a command line argument.");
			System.exit(1);
		}
		String fileName = args[0];
		LineNumberReader reader = new LineNumberReader(new FileReader(fileName));
		List<AccountNumber> accounts = read(reader);
		
		System.out.println("Received " + accounts.size() + " accounts:");
		if (args.length > 1) {
			File outFile = new File(args[1]);
			System.out.println("Writing output to file: " + outFile.getCanonicalPath());
			PrintWriter writer = new PrintWriter(new FileWriter(outFile));
			for (AccountNumber next : accounts) {
				String status = formatStatus(next);
				writer.println(next.toString() + " " + status );
			}
			writer.close();
		}
	}

    public static List<AccountNumber> read(LineNumberReader reader) throws IOException {
        return AllAccountsText.parseAccounts(LineNumberReaderHelper.readAll(reader));
    }

	private static String formatStatus(AccountNumber next) {
		String status = "";
		if (!next.isValid()) {
			status = next.containsIllegible() ? "ILL" : "ERR";
		}
		return status;
	}

}
