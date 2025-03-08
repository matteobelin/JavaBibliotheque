package com.esgi.presentation.cli.auth;

import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.auth.login.LoginCliCommandNode;
import com.esgi.presentation.cli.auth.logout.LogoutCliCommandNode;
import com.esgi.presentation.cli.auth.signin.SigninCliCommandNode;

public class AuthCliCommandNode extends CliCommandNode {
    public static final String NAME = "auth";
    public static final String DESCRIPTION = "Provide commands to login, logout and create an account";

    public AuthCliCommandNode(
            LoginCliCommandNode loginCommand,
            LogoutCliCommandNode logoutCommand,
            SigninCliCommandNode signinCommand
    ) {
        super(NAME, DESCRIPTION);

        this.childrenCommands.add(loginCommand);
        this.childrenCommands.add(logoutCommand);
        this.childrenCommands.add(signinCommand);
    }
}
