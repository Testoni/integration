package org.testoni.exception;

public class CustomParseException extends RuntimeException {
    public CustomParseException(String message) {
        super(message);
    }

    public CustomParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
