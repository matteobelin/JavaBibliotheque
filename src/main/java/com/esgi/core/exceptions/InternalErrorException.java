package com.esgi.core.exceptions;

import lombok.Getter;

@Getter
public class InternalErrorException extends Exception {
    private final Throwable exceptionSource;

    public InternalErrorException(Throwable exceptionSource) {
        super(String.format("Internal Error: %s", exceptionSource.getMessage()));
        this.exceptionSource = exceptionSource;
    }
}
