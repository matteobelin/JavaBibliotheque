package com.esgi.core.exceptions;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("The credentials provided are incorrect.");
    }
}
