package org.cct.cp2019a.treasurehunt.util;

/**
 * A simple string utility class.
 */
public class StringUtils {

    /**
     * Validates if the string has a letter.
     * @param str original string
     * @return true if the first character is an alphabetical letter
     */
    public static boolean isLetter(String str) {
        return Character.isLetter(str.charAt(0));
    }

    /**
     * Return the word ' 0 ' for null for empty input or the original value.
     * @param value original value
     * @return
     */
    public static String zeroOrValue(String value) {
        return (value == null || value.trim().isEmpty()) ? " 0 " : String.format("%03d", Integer.parseInt(value));
    }

}
