package com.esgi.data;

import lombok.Getter;

public enum SQLComparator {
    EQUALS("="),
    IS("IS");

    @Getter
    private final String value;

    SQLComparator(String value) {
        this.value = value;
    }
}
