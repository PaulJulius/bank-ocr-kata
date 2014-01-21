package com.pauljulius.bankocr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an Account Number like those read by the Bank OCR system. An account number is a series of digits.
 * Contains some useful methods for determining if the account number passes the checksum and possible permutations
 * that would be valid.
 * 
 */
public class AccountNumber {

	private final List<Digit> digits;

	public AccountNumber(String numbers) {
	    digits = 
	        Arrays.asList(numbers.split("(?<=\\G.)"))
    	        .stream()
    	        .map(s -> Digit.from(s))
    	        .collect(Collectors.toList());
	}
	
	public AccountNumber(List<Digit> found) {
		digits = found;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Digit next : digits) {
			builder.append(next.toString());
		}
		return builder.toString();
	}

	public boolean isValid() {
		if (containsIllegible()) return false;

		int sum = 0;
		for (int i = 0; i < 9; i++) {
			int multiplier = 9 - i;
			sum += digitAt(i) * multiplier ;
		}
		return sum % 11 == 0;
	}

	public boolean containsIllegible() {
	    return digits.stream().anyMatch(d -> !d.isValid());
	}

	private int digitAt(int i) {
		return digits.get(i).toInt();
	}

	public List<AccountNumber> validPermutations() {
	    List<AccountNumber> valids = new ArrayList<>();
	    
	    //for every digit
	    int index = 0;
	    for (Digit nextDigit : digits) {
            //for every digit alternative
	        for (Digit nextAlternative : nextDigit.alternatives()) {
	            //Create a new account number with the next alternative
	            AccountNumber possibleAccount = permuteDigit(index, nextAlternative);
	            //If the account is valid, then collect it
	            if (possibleAccount.isValid()) valids.add(possibleAccount);
	        }
	        index++;
	    }
        
	    return valids;
	}

	private AccountNumber permuteDigit(int index, Digit to) {
		List<Digit> permutedDigits = new ArrayList<Digit>(digits.size());
		permutedDigits.addAll(digits);
		permutedDigits.set(index, to);
		return new AccountNumber(permutedDigits);
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AccountNumber)) return false;
        
        AccountNumber other = (AccountNumber) obj;
        for (int i=0; i<9; i++) {
            if (!other.digits.get(i).equals(digits.get(i))) return false;
        }
        return true;
    }
}
