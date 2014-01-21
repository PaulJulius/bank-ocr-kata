package com.pauljulius.bankocr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper for parse a list of Strings, like those read from an input file into
 * a List of AccountNumber instances.
 */
public class AllAccountsText {

    public static List<AccountNumber> parseAccounts(List<String> lines) {
        return groupBy4(lines).stream()
                .map(next -> OneAccountText.parse(next))
                .collect(Collectors.toList());
    }
    
    private static List<String[]> groupBy4(List<String> lines) {
        List<String[]> groups = new ArrayList<>();
        while(lines.size() >=4) {
            groups.add(new String[]{
		            lines.get(0),
		            lines.get(1),
		            lines.get(2),
		            lines.get(3),
		    });
		    lines = lines.subList(4, lines.size());
		}
        return groups;
    }

		
}