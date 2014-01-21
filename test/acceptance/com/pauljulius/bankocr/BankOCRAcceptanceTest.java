package com.pauljulius.bankocr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import org.junit.Test;

public class BankOCRAcceptanceTest {
	
	@Test
	public void should_parse_valid_file() throws IOException {
		InputStream foo = this.getClass().getResourceAsStream("/com/pauljulius/bankocr/Story_1_Acceptance_Test_Data.txt");
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(foo));
		List<AccountNumber> numbers = BankOCR.read(reader);
		assertNotNull("Should never be null.", numbers);
		assertEquals("The test file should contain 11 entries", 11, numbers.size());
	}
	
	
}
