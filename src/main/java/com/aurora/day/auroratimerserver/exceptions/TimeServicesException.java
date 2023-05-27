package com.aurora.day.auroratimerserver.exceptions;

public class TimeServicesException extends RuntimeException {

    private static final long serialVersionUID = -2L;

    public TimeServicesException() {
    }

    public TimeServicesException(String message) {
        super(message);
    }
}
