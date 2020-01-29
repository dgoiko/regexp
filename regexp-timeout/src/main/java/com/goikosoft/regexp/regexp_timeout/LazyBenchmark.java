package com.goikosoft.regexp.regexp_timeout;

import java.util.regex.Matcher;

/**
 * This is a very basic benchmark. Warms up
 * @author Dario
 *
 */
public class LazyBenchmark {

	
	private static final String catastrophicString = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	private static final String longRunningString = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx"; // This should run for 0-10 seconds.
	private static final String catastrophicBacktracking = "(x+x+)+y";
	
	
	// demonstrates behavior for regular expression running into catastrophic backtracking for given input
    public static void main(String[] args) {
    	// Warmup phase
    	System.out.println("Warmup phase starting");
    	System.err.println("Printing to stderr");
    	System.currentTimeMillis();
    	Matcher matcher;
    	for(int i=0; i<2; i++) {
    		matcher = RegularExpressionUtils.createMatcherWithTimeout(catastrophicString, catastrophicBacktracking, 1000, 30000000);
            try {
                System.out.println(matcher.matches());
            } catch (RuntimeException ignore) {
            }
            matcher = RegularExpressionUtils.createMatcherWithTimeout(longRunningString, catastrophicBacktracking, -1, 30000000);
            try {
                System.out.println(matcher.matches());
            } catch (RuntimeException ignore) {
            }
    	}
    	

    	System.out.println("Warmup phase over");

        long millis = System.currentTimeMillis();
        
    	for(int i=0; i<10; i++) {
    		matcher = RegularExpressionUtils.createMatcherWithTimeout(longRunningString, catastrophicBacktracking, 100000, 30000000);
            try {
            	matcher.matches();
            } catch (RuntimeException ignore) {
            }
    	}
    	
    	System.out.println("Time with timeout configured " + (System.currentTimeMillis() - millis) + " milliseconds");
    	
        millis = System.currentTimeMillis();
        for(int i=0; i<10; i++) {
    		matcher = RegularExpressionUtils.createMatcherWithTimeout(longRunningString, catastrophicBacktracking, -1, 30000000);
            try {
            	matcher.matches();
            } catch (RuntimeException ignore) {
            }
    	}
    	System.out.println("Time without timeout configured " + (System.currentTimeMillis() - millis) + " milliseconds");
    }
	
}
