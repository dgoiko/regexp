package com.goikosoft.regexp.regexp_timeout;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Allows to create timeoutable regular expressions.
 *
 * Limitations: Can only throw RuntimeException. Decreases performance.
 *
 * Posted by Kris in stackoverflow.
 *
 * Modified by dgoiko to  ejecute timeout check only every n chars.
 * Now timeout < 0 means no timeout.
 * 
 * 
 * Keep in mind that after throwing a RuntimeException inside a match method, the state of the Matcher and the Pattern
 * may become totally inconsistent.
 * 
 *
 * @author Kris https://stackoverflow.com/a/910798/9465588
 *
 */
public class RegularExpressionUtils {

    

    public static Matcher createMatcherWithTimeout(String stringToMatch, String regularExpression, long timeoutMillis,
                                                      int checkInterval) {
        return createMatcherWithTimeout(stringToMatch, Pattern.compile(regularExpression), timeoutMillis, checkInterval);
    }

    public static Matcher createMatcherWithTimeout(String stringToMatch, Pattern regularExpressionPattern,
                                                    long timeoutMillis, int checkInterval) {
        if (timeoutMillis < 0) {
            return regularExpressionPattern.matcher(stringToMatch);
        }
        CharSequence charSequence = new TimeoutRegexCharSequence(stringToMatch, timeoutMillis, stringToMatch,
                regularExpressionPattern.pattern(), checkInterval);
        return regularExpressionPattern.matcher(charSequence);
    }

}