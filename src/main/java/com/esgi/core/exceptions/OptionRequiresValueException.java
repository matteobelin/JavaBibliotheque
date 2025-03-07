package com.esgi.core.exceptions;

public class OptionRequiresValueException extends InvalidArgumentException {
    public OptionRequiresValueException(String arg) {
        super(String.format("The option `%s` requires a value.", arg));
    }
}
