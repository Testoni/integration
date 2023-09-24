package org.testoni.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testoni.exception.CustomParseException;
import org.testoni.model.Order;
import org.testoni.model.User;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsParserTest {

    private static UtilsParser utilsParser;

    @BeforeAll
    public static void setUp() {
        utilsParser = new UtilsParser();
    }

    @Test
    public void shouldConvertStringToLong() throws CustomParseException {
        long result = utilsParser.stringToLong("12345", true);
        assertEquals(12345L, result);
    }

    @Test
    public void shouldGiveExceptionWhenConvertStringToLong() {
        String input = "xxx";
        CustomParseException exception = assertThrows(CustomParseException.class, () -> {
            utilsParser.stringToLong("xxx", false);
        });
        assertTrue(exception.getMessage().contains("Error parsing the string to long: " + input));
    }

    @Test
    public void shouldConvertStringToDouble() throws CustomParseException {
        double result = utilsParser.stringToDouble("12345");
        assertEquals(12345D, result);
    }

    @Test
    public void shouldGiveExceptionWhenConvertStringToDouble() {
        String input = "xxx";
        CustomParseException exception = assertThrows(CustomParseException.class, () -> {
            utilsParser.stringToDouble("xxx");
        });
        assertTrue(exception.getMessage().contains("Error parsing the string to double: " + input));
    }

    @Test
    public void shouldConvertObjectToJson() throws CustomParseException {
        User user = new User(1l, "Testoni", Arrays.asList(new Order()));
        String json = utilsParser.toJson(user);
        assertNotNull(json);

        String expectedJson = "{\"userId\":1,\"name\":\"Testoni\",\"orders\":[{\"orderId\":null,\"total\":null,\"date\":null,\"products\":null}]}";
        assertEquals(json, expectedJson);
    }

    @Test
    public void shouldGiveExceptionWhenConvertObjectToJson() {
        CustomParseException exception = assertThrows(CustomParseException.class, () -> {
            utilsParser.toJson(new Object());
        });
        assertTrue(exception.getMessage().contains("Error converting object to JSON:"));
    }
}
