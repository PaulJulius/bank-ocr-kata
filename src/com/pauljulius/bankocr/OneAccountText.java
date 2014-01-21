package com.pauljulius.bankocr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts a text based digital representation of an account number, like those read by the Bank OCR, and
 * converts them into Account Number instances.
 * 
 */
public class OneAccountText {

    public static AccountNumber parse(String[] fourLines) {
        List<Digit> digits = 
                displays(fourLines[0], fourLines[1], fourLines[2])
                    .stream()
                    .map(d -> Digit.from(d))
                    .collect(Collectors.toList());
        return new AccountNumber(digits);
    }
    
    private static List<SevenSegmentDisplay> displays(String topLine, String middleLine, String bottomLine) {
        String[] tops = topLine.split("(?<=\\G...)");
        String[] mids = middleLine.split("(?<=\\G...)");
        String[] bots = bottomLine.split("(?<=\\G...)");
        
        List<SevenSegmentDisplay> displays = new ArrayList<SevenSegmentDisplay>();
        for (int i = 0; i < tops.length; i++) {
            displays.add(SevenSegmentDisplay.parse(tops[i] + mids[i] + bots[i]));
        } 
        return displays;
    }

}
