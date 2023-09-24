package org.testoni.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testoni.exception.CustomParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;

public class UtilsParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static long stringToLong(String input, Boolean removeZeroAtLeft) throws CustomParseException {
        try {
            input = removeZeroAtLeft ? input : input;
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new CustomParseException("Error parsing the string to long: " + input, e);
        }
    }

    public static double stringToDouble(String input) throws CustomParseException {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CustomParseException("Error parsing the string to double: " + input, e);
        }
    }

    public static String toJson(Object object) throws CustomParseException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomParseException("Error converting object to JSON: " + e.getMessage(), e);
        }
    }

    public static LocalDate stringToLocalDate(String dateStr, String format) throws DateTimeParseException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            throw new CustomParseException("Error parsing string to date: " + e.getMessage(), e);
        }
    }

    public static String dateToString(LocalDate date, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (UnsupportedTemporalTypeException e) {
            throw new CustomParseException("Error parsing date to string: " + e.getMessage(), e);
        }
    }
}
