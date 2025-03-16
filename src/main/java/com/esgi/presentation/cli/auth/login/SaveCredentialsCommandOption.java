package com.esgi.presentation.cli.auth.login;

import com.esgi.presentation.cli.CliCommandNodeOption;

public class SaveCredentialsCommandOption extends CliCommandNodeOption {
    public static final String NAME = "save";
    public static final String SHORT_NAME = "s";
    public static final String DESCRIPTION = "Save the session to avoid logging in repeatedly";

    public SaveCredentialsCommandOption() {
        super(NAME, SHORT_NAME, DESCRIPTION, false);
    }
}
