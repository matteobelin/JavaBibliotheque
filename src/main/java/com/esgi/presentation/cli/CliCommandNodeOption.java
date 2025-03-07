package com.esgi.presentation.cli;

import lombok.Getter;
import lombok.Setter;

public abstract class CliCommandNodeOption {
    @Getter
    private final String name;

    @Getter
    private final String shortName;

    @Getter
    private final String description;

    private final boolean requiresValue;

    @Getter
    @Setter
    protected String value;

    public CliCommandNodeOption(String name, String shortName, String description, boolean requiresValue) {
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.requiresValue = requiresValue;
    }

    public CliCommandNodeOption(String name, String shortName, String description, boolean requiresValue, String value) {
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.requiresValue = requiresValue;
        this.value = value;
    }

    public boolean requiresValue() {
        return requiresValue;
    }
}
