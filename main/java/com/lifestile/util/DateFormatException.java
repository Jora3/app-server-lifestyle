package com.lifestile.util;

public class DateFormatException extends RuntimeException {

    DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
