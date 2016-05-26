package com.twilio.employeedirectory.domain.error;


public class EmployeeLoadException extends RuntimeException {

    public EmployeeLoadException(Throwable ex) {
        super("Unexpected exception: " + ex.getMessage(), ex);
    }

    public EmployeeLoadException(String message) {
        super(message);
    }
}
