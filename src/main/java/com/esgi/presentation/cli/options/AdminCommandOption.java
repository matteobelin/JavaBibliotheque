package com.esgi.presentation.cli.options;

import com.esgi.presentation.cli.CliCommandNodeOption;

public class AdminCommandOption extends CliCommandNodeOption {
    public static final String NAME = "admin";

    public static final String SHORT_NAME = "a";

    private final String description;

    public AdminCommandOption(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getShortName() {
        return SHORT_NAME;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
