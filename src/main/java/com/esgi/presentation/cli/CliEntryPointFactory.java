package com.esgi.presentation.cli;

import com.esgi.presentation.cli.auth.AuthCliCommandNodeFactory;
import com.esgi.presentation.cli.users.UserCliCommandFactory;

public final class CliEntryPointFactory {

    public static CliEntryPoint makeCliEntryPoint() {
        var userCliCommandNode = UserCliCommandFactory.makeUserCliCommandNode();
        var authCliCommandNode = AuthCliCommandNodeFactory.makeAuthCliCommandNode();

        return new CliEntryPoint(authCliCommandNode, userCliCommandNode);
    }
}
