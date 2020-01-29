package com.goikosoft.regexp.regexp_timeout;

/**
 * Allows to create timeoutable CharSequences.
 *
 * Limitations: Can only throw RuntimeException. Decreases performance.
 *
 * Posted by Kris in stackoverflow.
 *
 * Modified by dgoiko to  ejecute timeout check only every checkInterval chars.
 *
 * @author Kris https://stackoverflow.com/a/910798/9465588
 */
public class TimeoutRegexCharSequence implements CharSequence {

    private final CharSequence inner;

    private final long timeoutMillis;

    private final long timeoutTime;

    private final String stringToMatch;

    private final String regularExpression;

    private int checkInterval;

    private int attemps;

    TimeoutRegexCharSequence(CharSequence inner, long timeoutMillis, String stringToMatch,
                              String regularExpression, int checkInterval) {
        super();
        this.inner = inner;
        this.timeoutMillis = timeoutMillis;
        this.stringToMatch = stringToMatch;
        this.regularExpression = regularExpression;
        timeoutTime = System.currentTimeMillis() + timeoutMillis;
        this.checkInterval = checkInterval;
        this.attemps = 0;
    }

    public char charAt(int index) {
        if (this.attemps == this.checkInterval) {
            if (System.currentTimeMillis() > timeoutTime) {
                throw new RegexpTimeoutException(regularExpression, stringToMatch, timeoutMillis);
            }
            this.attemps = 0;
        } else {
            this.attemps++;
        }

        return inner.charAt(index);
    }

    public int length() {
        return inner.length();
    }

    public CharSequence subSequence(int start, int end) {
        return new TimeoutRegexCharSequence(inner.subSequence(start, end), timeoutMillis, stringToMatch,
                                            regularExpression, checkInterval);
    }

    @Override
    public String toString() {
        return inner.toString();
    }
}