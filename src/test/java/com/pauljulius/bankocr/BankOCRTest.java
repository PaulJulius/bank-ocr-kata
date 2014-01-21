package com.pauljulius.bankocr;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

public class BankOCRTest {

	@Test
	public void should_fail_when_line_size_incorrect() throws IOException {
		String input = 			   
				   "    _  _     _  _  _  _  _\n";
		LineNumberReader reader = new LineNumberReader(new StringReader(input));
		try {
			BankOCR.read(reader);
			fail("Should have caught an exception.");
		} catch (IllegalArgumentException e) {
			assertEquals("Line number 1 contains only 26 characters, but it should contain 27.", e.getMessage());
		}
	}

	@Test
	public void should_parse_valid_stream_when_one_account() throws IOException {
		String input = 			   
				   "    _  _     _  _  _  _  _ \n"
				 + "  | _| _||_||_ |_   ||_||_|\n"
				 + "  ||_  _|  | _||_|  ||_| _|\n"
				 + "                           \n";
		LineNumberReader reader = new LineNumberReader(new StringReader(input));
		List<AccountNumber> numbers = BankOCR.read(reader);
		assertNotNull("Should never be null.", numbers);
		assertEquals(1, numbers.size());
		assertEquals("123456789", numbers.get(0).toString());

	}
	
	@Test
	public void should_parse_valid_stream_when_two_accounts() throws IOException {
		String input = 			   
				   "    _  _     _  _  _  _  _ \n"
				 + "  | _| _||_||_ |_   ||_||_|\n"
				 + "  ||_  _|  | _||_|  ||_| _|\n"
				 + "                           \n"
		  		 + "    _  _     _  _  _  _  _ \n"
		  		 + "  | _| _||_||_ |_   ||_||_|\n"
		  		 + "  ||_  _|  | _||_|  ||_| _|\n"
		  		 + "                           \n";
		LineNumberReader reader = new LineNumberReader(new StringReader(input));
		List<AccountNumber> numbers = BankOCR.read(reader);
		assertNotNull("Should never be null.", numbers);
		assertEquals(2, numbers.size());
		assertEquals("123456789", numbers.get(0).toString());
		assertEquals("123456789", numbers.get(1).toString());
	}
}
