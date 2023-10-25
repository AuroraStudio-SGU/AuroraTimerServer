package com.aurora.day.auroratimerserver.exceptions;

//其实没什么用讲真(
public class UserServicesException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public UserServicesException() {
    }

    public UserServicesException(String message) {
        super(message);
    }
}
