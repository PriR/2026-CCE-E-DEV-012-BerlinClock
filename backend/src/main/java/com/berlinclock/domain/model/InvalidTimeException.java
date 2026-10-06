package com.berlinclock.domain.model;

/** Raised when a time of day is out of range or not written as {@code HH:mm:ss}. */
public class InvalidTimeException extends IllegalArgumentException {

    public InvalidTimeException(String message) {
        super(message);
    }
}
