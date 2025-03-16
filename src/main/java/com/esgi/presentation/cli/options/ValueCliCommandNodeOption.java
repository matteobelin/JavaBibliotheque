package com.esgi.presentation.cli.options;

import com.esgi.presentation.cli.CliCommandNodeOption;

public class ValueCliCommandNodeOption extends CliCommandNodeOption {

    public ValueCliCommandNodeOption(String name, String shortName, String description) {
        super(name, shortName, description, true);
    }
}
