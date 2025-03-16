package com.esgi.presentation.cli.options;

import com.esgi.presentation.cli.CliCommandNodeOption;

public class AdminCommandOption extends CliCommandNodeOption {
    public static final String NAME = "admin";
    public static final String SHORT_NAME = "a";

    public AdminCommandOption(String description) {
        super(NAME, SHORT_NAME, description, false);
    }

    public AdminCommandOption(String description, boolean requireValue) {
        super(NAME, SHORT_NAME, description, requireValue);
    }
}
