package org.cct.cp2019a.treasurehunt.util;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsTest {

    @Test
    void isInteger() {
        assertTrue(NumberUtils.isInteger("1"));
        assertTrue(NumberUtils.isInteger("9"));
        assertTrue(NumberUtils.isInteger("0"));
        assertFalse(NumberUtils.isInteger("09"));
        assertFalse(NumberUtils.isInteger("B"));
        assertFalse(NumberUtils.isInteger("A3"));
    }

    @Test
    void formatCurrency() {
        Locale.setDefault(Locale.FRANCE);
        String value = NumberUtils.formatCurrency(2.4);
        assertNotNull(value);
        assertEquals("2,40 €", value);
    }

    @Test
    void getRandomNumberInRange() {
        int number = NumberUtils.getRandomNumberInRange(1, 10);
        assertTrue(NumberUtils.isBetween(number, 1, 10));
        assertNotEquals(number, 0);
        assertNotEquals(number, 11);
    }

    @Test
    void isBetween() {
        assertTrue(NumberUtils.isBetween(2, 1, 3));
        assertFalse(NumberUtils.isBetween(4, 1, 3));
    }
}