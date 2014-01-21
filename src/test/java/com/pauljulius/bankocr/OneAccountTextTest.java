package com.pauljulius.bankocr;

import static org.junit.Assert.*;

import org.junit.Test;

public class OneAccountTextTest {

	@Test
	public void should_parse_all_zeros() {
		assertParsesTo("000000000",  
				 " _  _  _  _  _  _  _  _  _ ",
				 "| || || || || || || || || |",
		         "|_||_||_||_||_||_||_||_||_|",
				 "                           "
		);
	}

	private void assertParsesTo(String expected, String... fourLines) {
		AccountNumber result = OneAccountText.parse(fourLines);
		assertEquals(expected, result.toString());
	}

	@Test
	public void should_parse_all_ones() {
		assertParsesTo("111111111", 
				 "                           ",
				 "  |  |  |  |  |  |  |  |  |", 
			     "  |  |  |  |  |  |  |  |  |",
				 "                           "
		);
	}
	
	@Test
	public void should_parse_all_twos() {
		assertParsesTo("222222222", 
				 " _  _  _  _  _  _  _  _  _ ", 
				 " _| _| _| _| _| _| _| _| _|",
				 "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				 "                           "
		);
	}

	@Test
	public void should_parse_all_threes() {
		assertParsesTo("333333333",
				" _  _  _  _  _  _  _  _  _ ", 
				" _| _| _| _| _| _| _| _| _|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		);
	}
	
	
	@Test
	public void should_parse_all_fours() {
		assertParsesTo("444444444",
				"                           ",
				"|_||_||_||_||_||_||_||_||_|",
				"  |  |  |  |  |  |  |  |  |",
				"                           "
		);
	}
	
	@Test
	public void should_parse_all_fives() {
		assertParsesTo("555555555",
				 " _  _  _  _  _  _  _  _  _ ", 
				 "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				 " _| _| _| _| _| _| _| _| _|",
				 "                           "		
		);
	}
	
	@Test
	public void should_parse_all_sixes() {
		assertParsesTo("666666666",
				 " _  _  _  _  _  _  _  _  _ ", 
				 "|_ |_ |_ |_ |_ |_ |_ |_ |_ ",
				 "|_||_||_||_||_||_||_||_||_|",
				 "                           "
		);
	}
	
	@Test
	public void should_parse_all_sevens() {
		assertParsesTo("777777777",
				 " _  _  _  _  _  _  _  _  _ ", 
				 "  |  |  |  |  |  |  |  |  |",
				 "  |  |  |  |  |  |  |  |  |",
				 "                           "
		);
	}
	
	@Test
	public void should_parse_all_eights() {
		assertParsesTo("888888888",
				" _  _  _  _  _  _  _  _  _ ",
				"|_||_||_||_||_||_||_||_||_|",
				"|_||_||_||_||_||_||_||_||_|",
				"                           "
		);
	}
	
	@Test
	public void should_parse_all_nines() {
		assertParsesTo("999999999",
				" _  _  _  _  _  _  _  _  _ ", 
				"|_||_||_||_||_||_||_||_||_|",
				" _| _| _| _| _| _| _| _| _|",
				"                           "
		);
	}
	
	@Test
	public void should_parse_one_through_nine() {
		assertParsesTo("123456789",
		        "    _  _     _  _  _  _  _ ",
		        "  | _| _||_||_ |_   ||_||_|",
		        "  ||_  _|  | _||_|  ||_| _|",
		        "                           "
		);
	}

	@Test
	public void should_parse_but_insert_question_mark_for_illegible() {
		assertParsesTo("49006771?",
			    "    _  _  _  _  _  _     _ ",
			    "|_||_|| || ||_   |  |  | _ ",
			    "  | _||_||_||_|  |  |  | _|",
			    "                           "
		);
	}
	
	@Test
	public void should_parse_but_insert_multiple_question_mark_for_illegible() {
		assertParsesTo("1234?678?",
			    "    _  _     _  _  _  _  _ ", 
			    "  | _| _||_| _ |_   ||_||_|",
			    "  ||_  _|  | _||_|  ||_| _ ",
			    "                           "
		);
	}
}
