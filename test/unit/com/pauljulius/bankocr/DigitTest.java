package com.pauljulius.bankocr;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class DigitTest {

	@Test
	public void should_parse_zero() {
		assertParsesTo(0, 
			  " _ ",
			  "| |", 
		      "|_|",
			  "   ");
	}

	@Test
	public void should_parse_one() {
		assertParsesTo(1, 
			  "   ",
			  "  |", 
		      "  |",
			  "   ");
	}
	
	@Test
	public void should_parse_two() {
		assertParsesTo(2, 
			  " _ ",
			  " _|", 
		      "|_ ",
			  "   ");
	}
	
	@Test
	public void should_parse_three() {
		assertParsesTo(3,
			  " _ ",
			  " _|", 
		      " _|",
			  "   ");
	}
	
	@Test
	public void should_parse_four() {
		assertParsesTo(4,
			  "   ",
			  "|_|", 
		      "  |",
			  "   ");
	}
	
	@Test
	public void should_parse_five() {
		assertParsesTo(5,
			  " _ ",
			  "|_ ", 
		      " _|",
			  "   ");
	}
	
	@Test
	public void should_parse_six() {
		assertParsesTo(6,
			  " _ ",
			  "|_ ", 
		      "|_|",
			  "   ");
	}

	@Test
	public void should_parse_seven() {
		assertParsesTo(7,
			  " _ ",
			  "  |", 
		      "  |",
			  "   ");
	}
	
	@Test
	public void should_parse_eight() {
		assertParsesTo(8,
			  " _ ",
			  "|_|", 
		      "|_|",
			  "   ");
	}
	
	@Test
	public void should_parse_nine() {
		assertParsesTo(9,
			  " _ ",
			  "|_|", 
		      " _|",
			  "   ");
	}
	
	@Test
	public void should_find_alternatives_for_almost_zero_and_nine() {
	    String[] pattern = { 
	            " _ ",
	            "| |", 
	            " _|",
	            "   "
	    };
	    Digit found = Digit.from(SevenSegmentDisplay.parse(pattern[0] + pattern[1] + pattern[2]));
	    assertFalse(found.isValid());
	    Set<Digit> alternatives = found.alternatives();
	    assertNotNull("Should never be null", alternatives);
	    assertEquals(2, alternatives.size());
	    assertTrue(alternatives.contains(Digit.NINE));
	    assertTrue(alternatives.contains(Digit.ZERO));
	}
	
	@Test
    public void should_find_alternatives_for_one() {
        String[] pattern = { 
                "   ",
                "  |", 
                "  |",
                "   "
        };
        Digit found = Digit.from(SevenSegmentDisplay.parse(pattern[0] + pattern[1] + pattern[2]));
        assertTrue("Found an invalid digit: " + found, found.isValid());
        Set<Digit> alternatives = found.alternatives();
        assertNotNull("Should never be null", alternatives);
        assertEquals(1, alternatives.size());
        assertTrue("Found wrong alternatives: " + alternatives, alternatives.contains(Digit.SEVEN));
    }
	
	@Test
    public void should_find_alternatives_for_big_backwards_c() {
        String[] pattern = { 
                " _ ",
                "  |", 
                " _|",
                "   "
        };
        Digit found = Digit.from(SevenSegmentDisplay.parse(pattern[0] + pattern[1] + pattern[2]));
        assertFalse(found.isValid());
        assertAlternativesAre(found, Digit.SEVEN, Digit.THREE);
    }
	
	@Test
    public void should_find_alternatives_for_floating_one() {
        String[] pattern = { 
                "   ",
                "  |", 
                "   ",
                "   "
        };
        Digit found = Digit.from(SevenSegmentDisplay.parse(pattern[0] + pattern[1] + pattern[2]));
        assertFalse(found.isValid());
        assertAlternativesAre(found, Digit.ONE);
    }

    private void assertAlternativesAre(Digit origin, Digit...desiredAlternatives) {
        Set<Digit> alternatives = origin.alternatives();
        assertEquals("Wrong number of alternatives found.", desiredAlternatives.length, alternatives.size());
        for (Digit next : alternatives) {
            assertTrue("Should have found " + next + " as an alternative." , alternatives.contains(next));
        }
        
    }
	
	private void assertParsesTo(int expected, String... fourLines) {
        Digit result = Digit.from(SevenSegmentDisplay.parse(fourLines[0] + fourLines[1] + fourLines[2]));
        assertTrue("Expected a valid digit to be found", result.isValid());
        assertEquals(expected, result.toInt());
    }
}
