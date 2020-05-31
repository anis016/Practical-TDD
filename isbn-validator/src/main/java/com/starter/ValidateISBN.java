package com.starter;

public class ValidateISBN {

    public boolean check10DigitISBN(String isbn) {
        long sum = 0;
        int digit;
        for (int i = 0; i < 10; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                // last character could be X
                if (i == 9 && isbn.charAt(9) == 'X') {
                    digit = 10;
                } else {
                    throw new NumberFormatException("ISBN must be numeric digits");
                }
            } else {
                // digit = isbn.charAt(i) - '0';
                digit = Character.getNumericValue(isbn.charAt(i));
            }
            sum += digit * (10 - i);
        }
        return sum % 11 == 0;
    }

    public boolean check13DigitISBN(String isbn) {
        long sum = 0;
        int digit;
        for (int i = 0; i < 12; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                throw new NumberFormatException("ISBN must be numeric digits");
            }
            digit = Character.getNumericValue(isbn.charAt(i));
            if (i % 2 == 0) {
                sum += digit; // multiply by 1
            } else {
                sum += (digit * 3); // multiply by 3
            }
        }
        long modulo10 = sum % 10;
        long checkDigit = -1;
        if (modulo10 == 0) {
            checkDigit = 0;
        } else {
            checkDigit = 10 - modulo10;
        }

        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }

    public boolean checkISBN(String isbn) {
        boolean isValid = false;
        if (isbn.length() == 10) {
            isValid = check10DigitISBN(isbn);
        } else if (isbn.length() == 13) {
            isValid = check13DigitISBN(isbn);
        } else {
            throw new NumberFormatException("ISBN length must be either 10 or 13 digits long");
        }
        return isValid;
    }

    public static void main(String[] args) {
        ValidateISBN isbn = new ValidateISBN();
        boolean isValid = isbn.checkISBN("0140449117");
        System.out.println(isValid);
    }

}
