package com.esgi.presentation.cli;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class CliCommandNodeOption {

    protected String value;

    public CliCommandNodeOption() {};
    public CliCommandNodeOption(String value) {
        this.value = value;
    };

    public abstract String getName();

    public abstract String getShortName();

    public abstract String getDescription();
}
