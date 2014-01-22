package com.pauljulius.bankocr;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The main class for the Bank OCR application. It offers a command line that accepts an input file, processes all the
 * account numbers, and optionally writes them to an output file.
 */
public class BankOCR {
    
    private static final Options options = new Options();
    static {
        Option inputfile = new Option("input", true, "Input file to parse for Bank Account records");
        inputfile.setRequired(true);
        Option outputfile = new Option("output", true, "Input file to parse for Bank Account records");
        Option help = new Option("help", "Print out this help message");
        options.addOption(inputfile);
        options.addOption(outputfile);
        options.addOption(help);
    }
	
    public static void main(String[] args) throws IOException {
        
        CommandLine cli = parse(args);
		
        String fileName = cli.getOptionValue("input");
		LineNumberReader reader = new LineNumberReader(new FileReader(fileName));
		List<AccountNumber> accounts = read(reader);
		
		System.out.println("Received " + accounts.size() + " accounts:");
		
		optionalOutput(cli, accounts);
	}

    private static void optionalOutput(CommandLine cli, List<AccountNumber> accounts) throws IOException {
        if (cli.hasOption("output")) {
			File outFile = new File(cli.getOptionValue("output"));
			System.out.println("Writing output to file: " + outFile.getCanonicalPath());
			PrintWriter writer = new PrintWriter(new FileWriter(outFile));
			for (AccountNumber next : accounts) {
				write(next, writer);
			}
			writer.close();
		}
    }

    private static void write(AccountNumber next, PrintWriter writer) {
        if (next.isValid()) {
            writer.println(next.toString());
        } else if (next.hasOneOtherSolution()) {
            writer.println(next.firstPermutation().toString());
        } else if (next.isAmbiguous()) {
            writer.println(next.toString() + " AMB " + next.validPermutations());
        } else if (next.isIllegible()) {
            writer.println(next.toString() + " ILL");
        } else if (next.isInError()) {
            writer.println(next.toString() + " ERR");
        }
    }

    private static CommandLine parse(String[] args) {
        CommandLine cli = null;
        try {
            cli = new GnuParser().parse(options, args);
        } catch (ParseException e) {
            System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
            printHelp();
            System.exit(1);
        }
        
        if (cli.hasOption("help")) {
            printHelp();
            System.exit(0);
        }
        
        return cli;
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "bankocr", options );
    }

    public static List<AccountNumber> read(LineNumberReader reader) throws IOException {
        return AllAccountsText.parseAccounts(LineNumberReaderHelper.readAll(reader));
    }
}
