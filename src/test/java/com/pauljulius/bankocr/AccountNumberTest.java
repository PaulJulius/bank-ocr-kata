package com.pauljulius.bankocr;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class AccountNumberTest {


	@Test
	public void should_have_valid_checksum() {
		AccountNumber num = new AccountNumber("000000051");
		assertTrue(num.isValid());
	}
	
	@Test
	public void should_have_invalid_checksum() {
		AccountNumber num = new AccountNumber("664371495");
		assertFalse("Should fail the checksum verification.", num.isValid());
	}
	
	@Test
	public void should_have_invalid_checksum_when_illegible_digits() {
		AccountNumber num = new AccountNumber("6643?1495");
		assertFalse("Should fail the checksum verification.", num.isValid());
	}
	
	@Test
	public void should_find_valid_alternative_for_all_ones() {
		AccountNumber num = new AccountNumber("111111111");
		assertFalse("Should fail the checksum verification.", num.isValid());
		List<AccountNumber> permutations = num.validPermutations();
		assertEquals(1, permutations.size());
		assertEquals("711111111", permutations.get(0).toString());
	}
	
	@Test
    public void should_find_valid_alternatives_for_490067715() {
        AccountNumber num = new AccountNumber("490067715");
        assertFalse("Should fail the checksum verification.", num.isValid());
        List<AccountNumber> permutations = num.validPermutations();
        assertEquals(3, permutations.size());
        System.out.println("Permutations: " + permutations);
        assertTrue(permutations.contains(new AccountNumber("490067115")));
        assertTrue(permutations.contains(new AccountNumber("490067719")));
        assertTrue(permutations.contains(new AccountNumber("490867715")));
    }
	
	@Test
    public void should_find_valid_alternative_for_all_o_u_os_51() {
	    String[] accountRepresentation = { 
                " _     _  _  _  _  _  _    ",
                "| || || || || || || ||_   |",
                "|_||_||_||_||_||_||_| _|  |",
                "                           "
        };
        AccountNumber num = OneAccountText.parse(accountRepresentation);
        assertFalse("Should fail the checksum verification.", num.isValid());
        List<AccountNumber> permutations = num.validPermutations();
        assertEquals(1, permutations.size());
        assertEquals("000000051", permutations.get(0).toString());
    }
	
	@Test
    public void should_find_valid_alternative_for_almost_490867715() {
        String[] accountRepresentation = { 
                "    _  _  _  _  _  _     _ ", 
                "|_||_|| ||_||_   |  |  | _ ",
                "  | _||_||_||_|  |  |  | _|",
                "                           "
        };
        AccountNumber num = OneAccountText.parse(accountRepresentation);
        assertFalse("Should fail the checksum verification.", num.isValid());
        List<AccountNumber> permutations = num.validPermutations();
        assertEquals(1, permutations.size());
        assertEquals("490867715", permutations.get(0).toString());
    }
}
