package com.aurora.day.auroratimerservernative.exceptions;

//其实没什么用讲真(
public class TimeServicesException extends RuntimeException {

    private static final long serialVersionUID = -2L;

    public TimeServicesException() {
    }

    public TimeServicesException(String message) {
        super(message);
    }
}
