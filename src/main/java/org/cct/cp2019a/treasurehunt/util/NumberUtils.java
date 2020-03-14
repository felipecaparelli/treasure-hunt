package org.cct.cp2019a.treasurehunt.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 * An utility class for number operations.
 */
public class NumberUtils {

    /**
     * Validates if the value found on the string first character is a number.
     * @param str
     * @return true if the value is an valid integer number (digit)
     */
    public static boolean isInteger(String str) {
        return Character.isDigit(str.charAt(0));
    }

    /**
     * Format the double value to currency according to the system locale.
     * @param value the value to be formatted
     * @return the currency formatted value
     */
    public static String formatCurrency(Double value) {
        Locale locale = Locale.getDefault();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(value);
    }

    /**
     * This method generates random integer numbers between the min and max value.
     * @param min minimal value allowed
     * @param max maximal value allowed
     * @return an integer number between the min and max values
     */
    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * This method validates if the number para is between min and max arguments.
     * @param number the number value
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the number is between min and max
     */
    public static boolean isBetween(int number, int min, int max) {
        return number >= min && number <= max;
    }
}
