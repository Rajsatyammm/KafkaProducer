package com.hcl.producer.exceptions;

public class UserAlreadyExists extends RuntimeException {
    private String message;

    public UserAlreadyExists(String message) {
        super(message);
        this.message = message;
    }
}
