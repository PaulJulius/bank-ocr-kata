package com.pauljulius.bankocr;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Converts a text pattern into an object that represents the Segments of a number on an old fashioned
 * digital display, like an old calculator, or as read by the Bank OCR application.
 * 
 * See the Wikipedia article for an in-depth analysis.
 * 
 * http://en.wikipedia.org/wiki/Seven-segment_display
 */
public class SevenSegmentDisplay {
    
    private final boolean top;
    private final boolean middle;
    private final boolean bottom;
    private final boolean upperLeft;
    private final boolean upperRight;
    private final boolean lowerLeft;
    private final boolean lowerRight;
    private Collection<SevenSegmentDisplay> allFlipped;

    private SevenSegmentDisplay(boolean top, boolean mid, boolean bot,
            boolean upLeft, boolean upRight, boolean lowLeft, boolean lowRight) {
        this.top = top;
        this.middle = mid;
        this.bottom = bot;
        this.upperLeft = upLeft;
        this.upperRight = upRight;
        this.lowerLeft = lowLeft;
        this.lowerRight = lowRight;
    }

    /**
     * Example of a zero:
     * 
     * " _ " +
     * "| |" +
     * "|_|"
     */
    public static SevenSegmentDisplay parse(String pattern) {
        boolean top = pattern.charAt(1) == '_';
        boolean middle = pattern.charAt(4) == '_';
        boolean bottom = pattern.charAt(7) == '_';
        boolean upLeft = pattern.charAt(3) == '|';
        boolean upRight = pattern.charAt(5) == '|';
        boolean lowLeft = pattern.charAt(6) == '|';
        boolean lowRight = pattern.charAt(8) == '|';
        return new SevenSegmentDisplay(top, middle, bottom, upLeft, upRight, lowLeft, lowRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof SevenSegmentDisplay)) return false;
        
        SevenSegmentDisplay other = (SevenSegmentDisplay)obj;
        return other.top == this.top
                && other.middle == this.middle
                && other.bottom == this.bottom
                && other.upperLeft == this.upperLeft
                && other.upperRight == this.upperRight
                && other.lowerRight == this.lowerRight
                && other.lowerLeft == this.lowerLeft;
    }

    public Collection<SevenSegmentDisplay> allFlipped() {
        if (allFlipped != null) return allFlipped;
        
        allFlipped = new ArrayList<SevenSegmentDisplay>();
        allFlipped.add(new SevenSegmentDisplay(!top, middle, bottom, upperLeft, upperRight, lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, !middle, bottom, upperLeft, upperRight, lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, middle, !bottom, upperLeft, upperRight, lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, middle, bottom, !upperLeft, upperRight, lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, middle, bottom, upperLeft, !upperRight, lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, middle, bottom, upperLeft, upperRight, !lowerLeft, lowerRight));
        allFlipped.add(new SevenSegmentDisplay(top, middle, bottom, upperLeft, upperRight, lowerLeft, !lowerRight));
        return allFlipped;
    }
}
