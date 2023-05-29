package com.aurora.day.auroratimerserver.exceptions;

public class AuthorityException extends RuntimeException {
    private static final long serialVersionUID = -2L;

    public AuthorityException() {
    }

    public AuthorityException(String message) {
        super(message);
    }
}
