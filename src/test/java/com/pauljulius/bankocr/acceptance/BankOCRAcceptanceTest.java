package com.pauljulius.bankocr.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.pauljulius.bankocr.AccountNumber;
import com.pauljulius.bankocr.BankOCR;
import com.pauljulius.bankocr.LineNumberReaderHelper;

public class BankOCRAcceptanceTest {
	
	@Test
	public void should_accept_story_one() throws IOException {
		InputStream foo = this.getClass().getResourceAsStream("/Story_1_Acceptance_Test_Data.txt");
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(foo));
		List<AccountNumber> numbers = BankOCR.read(reader);
		assertNotNull("Should never be null.", numbers);
		assertEquals("The test file should contain 11 entries", 11, numbers.size());
	}
	
	@Test
    public void should_accept_story_three() throws IOException {
	    File tempFile = createTestingOutputFile();
        String args[] = {"-input", 
	            this.getClass().getResource("/Story_3_Acceptance_Test_Data.txt").getFile(),
	            "-output",
	            tempFile.getAbsolutePath()};
	    BankOCR.main(args);
	    
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(tempFile)));
        List<String> output = LineNumberReaderHelper.readAll(reader, Integer.MIN_VALUE);

        assertMatches(output, "000000051", "49006771? ILL", "1234?678? ILL");
    }
	
	@Test
	public void should_accept_story_four() throws IOException {
	    File tempFile = createTestingOutputFile();
        String args[] = {"-input", 
                this.getClass().getResource("/Story_4_Acceptance_Test_Data.txt").getFile(),
                "-output",
                tempFile.getAbsolutePath()};
        BankOCR.main(args);
        
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(tempFile)));
        List<String> output = LineNumberReaderHelper.readAll(reader, Integer.MIN_VALUE);

        assertMatches(output, 
                "711111111",
                "777777177",
                "200800000",
                "333393333",
                "888888888 AMB [888886888, 888888988, 888888880]",
                "555555555 AMB [559555555, 555655555]",
                "666666666 AMB [686666666, 666566666]",
                "999999999 AMB [899999999, 993999999, 999959999]",
                "490067715 AMB [490867715, 490067115, 490067719]",
                "123456789",
                "000000051",
                "490867715"
                );
    }

	/**
	 * Reverse ordering of the params from a normal assert, actual first, then expected.
	 */
    private void assertMatches(List<String> actual, String...expected) {
        assertEquals(Arrays.asList(expected), actual);
    }

	
    
    private File createTestingOutputFile() throws IOException {
        File tempFile = File.createTempFile(this.getClass().getName(), String.valueOf(System.currentTimeMillis()));
        tempFile.deleteOnExit();
        return tempFile;
    }
}
