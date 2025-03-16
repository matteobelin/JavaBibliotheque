package com.esgi.data.sql;

import lombok.Getter;

public enum SQLComparator {
    EQUALS("="),
    IS("IS"),
    LIKE("LIKE");

    @Getter
    private final String value;

    SQLComparator(String value) {
        this.value = value;
    }
}
