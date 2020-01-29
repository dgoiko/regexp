package com.goikosoft.regexp.regexp_timeout;

import java.util.regex.Matcher;

/**
 * A class to demonstrate behavior for regular expression running into catastrophic backtracking
 * 
 * @author Dario
 *
 */
public class ProofOfConcept {


	private static final String catastrophicString = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String catastrophicBacktracking = "(x+x+)+y";
    // This checkInterval produces a < 1000 ms delay. Higher checkInterval will produce higher delays on timeout.
	private static final int checkInterval = 30000000; 
	private static final long timeoutMillis = 10000;
	
	//  for given input
    public static void main(String[] args) {
        long millis = System.currentTimeMillis();
        Matcher matcher = RegularExpressionUtils.createMatcherWithTimeout(catastrophicString, catastrophicBacktracking, timeoutMillis, checkInterval);
        try {
            System.out.println(matcher.matches());
        } catch (RuntimeException e) {
            System.out.println("Operation timed out after " + (System.currentTimeMillis() - millis) + " milliseconds");
            return;
        }
        System.out.println("Operation completed out after " + (System.currentTimeMillis() - millis) + " milliseconds");
    }
	
}
