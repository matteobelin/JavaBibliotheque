package com.esgi.presentation;

public enum AppLoggerColorEnum {
    DEFAULT("\u001B[0m"),
    GREEN("\u001B[32m"),
    ORANGE("\u001B[33m"),
    BLUE("\u001B[34m"),
    RED("\u001B[31m");

    public final String value;

    AppLoggerColorEnum(String value) {
        this.value = value;
    }
}
