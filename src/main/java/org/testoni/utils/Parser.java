package org.testoni.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public static String toJson(Object object) throws NumberFormatException, CustomParseException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
