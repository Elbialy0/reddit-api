package com.elbialy.reddit.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
