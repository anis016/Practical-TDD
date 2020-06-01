package com.starter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestValidateISBN {
    static ValidateISBN validateISBN;

    // for BeforeAll the method needs to be static.
    // Call this method only once for creating object and then run all the @Test
    @BeforeAll
    public static void beforeAll() {
        validateISBN = new ValidateISBN();
    }

    // Call this method every time before executing any @Test
    /*@BeforeEach
    public void beforeEach() {
        validateISBN = new ValidateISBN();
    }*/

    @Test
    public void testISBNIsValid() {
        boolean isValid = validateISBN.checkISBN("0140449116");
        assertTrue(isValid, "first check");
        isValid = validateISBN.checkISBN("0140177396");
        assertTrue(isValid, "second check");
    }

    @Test
    public void test13DigitISBNIsValid() {
        boolean isValid = validateISBN.checkISBN("9781861972712");
        assertTrue(isValid);
    }

    @Test
    public void testISBNNumbersEndingInXIsValid() {
        boolean isValid = validateISBN.checkISBN("012000030X");
        assertTrue(isValid);
    }

    @Test
    public void testISBNNumbersEndingInXIsNotValid() {
        boolean isValid = validateISBN.checkISBN("012001030X");
        assertFalse(isValid);
    }

    @Test
    public void testISBNIsNotValid() {
        boolean isValid = validateISBN.checkISBN("0140449117");
        assertFalse(isValid);
    }

    @Test
    public void test13DigitISBNIsNotValid() {
        boolean isValid = validateISBN.checkISBN("9781861973712");
        assertFalse(isValid, "first check");
        isValid = validateISBN.checkISBN("9781681972712");
        assertFalse(isValid, "second check");
    }

    @Test
    public void testISBNInValidLength() {
        assertThrows(NumberFormatException.class,
                () -> {validateISBN.checkISBN("123456789");});
    }

    @Test
    public void test13DigitISBNInValidLength() {
        assertThrows(NumberFormatException.class,
                () -> {validateISBN.checkISBN("123456789123");});
    }

    @Test
    public void testNonNumericISBN() {
        assertThrows(NumberFormatException.class,
                () -> {validateISBN.checkISBN("helloworld");});
    }

    @Test
    public void test13DigitNonNumericISBN() {
        assertThrows(NumberFormatException.class,
                () -> {validateISBN.checkISBN("helloworldend");});
    }
}
