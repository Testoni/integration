package org.testoni.utils;

import org.testoni.exception.CustomParseException;

public class Parser {
    public static Long parseToLong(String input) throws NumberFormatException {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new CustomParseException("Error parsing the string to long: " + input, e);
        }
    }

    public static Double parseToDouble(String input) throws NumberFormatException, CustomParseException {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error parsing the string to double: " + input);
        }
    }
}
