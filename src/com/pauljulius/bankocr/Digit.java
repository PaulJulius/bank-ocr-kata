package com.pauljulius.bankocr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A single digit like those found in an AccountNumber instance. A digit might be a valid number, 0 through 9,
 * or could represent an illegible digit, as is the case when one segment is missing from an otherwise valid
 * SevenSegmentDisplay. Digits know how to offer alternatives for what it could be if only one segment was
 * changed in its SevenSegmentDisplay representation.
 * 
 */
public class Digit {

	public static final Digit ZERO  = 
	        new Digit("0", 
	        " _ "
	      + "| |"
          + "|_|");
	public static final Digit ONE = 
            new Digit("1", 
	        "   "
          + "  |"
          + "  |");
	public static final Digit TWO = 
            new Digit("2", 
	        " _ "
          + " _|"
          + "|_ ");
	public static final Digit THREE = 
            new Digit("3", 
	        " _ "
          + " _|"
          + " _|");
    public static final Digit FOUR = 
            new Digit("4", 
	        "   "
          + "|_|"
          + "  |");
    public static final Digit FIVE = 
            new Digit("5", 
	        " _ "
          + "|_ "
          + " _|");
    public static final Digit SIX = 
            new Digit("6", 
	        " _ "
          + "|_ "
          + "|_|");
    public static final Digit SEVEN = 
            new Digit("7", 
	        " _ "
          + "  |"
          + "  |");
    public static final Digit EIGHT = 
            new Digit("8", 
	        " _ "
          + "|_|"
          + "|_|");
    public static final Digit NINE = 
            new Digit("9", 
	        " _ "
          + "|_|"
          + " _|");
    public static final Digit ILLEGIBLE = 
            new Digit("?", 
            "   "
          + "   "
          + "   ", false);
	
    private static final List<Digit> standards;
    static {
        standards = new ArrayList<>();
        standards.add(ONE);
        standards.add(TWO);
        standards.add(THREE);
        standards.add(FOUR);
        standards.add(FIVE);
        standards.add(SIX);
        standards.add(SEVEN);
        standards.add(EIGHT);
        standards.add(NINE);
        standards.add(ZERO);
        standards.add(ILLEGIBLE);
    }
    
    private final String value;
    private final SevenSegmentDisplay rep;
    private final boolean isValid;

	private Digit(String value, String pattern) {
		this(value, pattern, true);
	}
	
	private Digit(String value, String pattern, boolean valid) {
	    this(value, SevenSegmentDisplay.parse(pattern), valid);
    }

    private Digit(String value, SevenSegmentDisplay positions, boolean valid) {
        this.value = value;
        this.isValid = valid;
        this.rep = positions;
    }

	public static Digit from(String value) {
	    //Java 8 lambda
        return standards.stream()
          .filter(d -> d.value.equals(value))
          .findFirst().orElseThrow(IllegalArgumentException::new);        
	}
	
	public static Digit from(SevenSegmentDisplay possible) {
        //Java 8 lambda
        return standards.stream()
          .filter(d -> d.rep.equals(possible))
          .findFirst()
          .orElse(new Digit("?", possible, false));
	}
	
    public Set<Digit> alternatives() {
        return rep.allFlipped().stream()
                .map(s -> Digit.from(s))
                .filter(d -> ((Digit) d).isValid())
                .collect(Collectors.toSet());
    }

    public boolean isValid() {
        return isValid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Digit)) return false;
        
        Digit other = (Digit) obj;
        return other.value.equals(value)
                && other.rep.equals(rep)
                && other.isValid == isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    public int toInt() {
        return Integer.parseInt(value);
    }


}
