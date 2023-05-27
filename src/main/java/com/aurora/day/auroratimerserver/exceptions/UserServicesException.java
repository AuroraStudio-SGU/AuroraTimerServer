package com.aurora.day.auroratimerserver.exceptions;

public class UserServicesException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public UserServicesException() {
    }

    public UserServicesException(String message) {
        super(message);
    }
}
