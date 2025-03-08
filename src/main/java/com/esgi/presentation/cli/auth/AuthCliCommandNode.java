package com.esgi.presentation.cli.auth;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.auth.login.LoginCliCommandFactory;

public class AuthCliCommandNode extends CliCommandNode {
    public static final String NAME = "auth";
    public static final String DESCRIPTION = "Provide commands to login, logout and create an account";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.PUBLIC;

    public AuthCliCommandNode() {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        var loginCommand = LoginCliCommandFactory.makeLoginCliCommandNode();
        this.childrenCommands.add(loginCommand);
    }
}
