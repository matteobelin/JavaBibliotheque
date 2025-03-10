package com.esgi.presentation.cli;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.cli.auth.AuthCliCommandNodeFactory;
import com.esgi.presentation.cli.authors.AuthorCliCommandFactory;
import com.esgi.presentation.cli.users.UserCliCommandFactory;

public final class CliEntryPointFactory {

    public static CliEntryPoint makeCliEntryPoint() {
        var authService = AuthServiceFactory.getAuthService();

        var userCliCommandNode = UserCliCommandFactory.makeUserCliCommandNode();
        var authCliCommandNode = AuthCliCommandNodeFactory.makeAuthCliCommandNode();
        var authorCliCommandNode = AuthorCliCommandFactory.makeAuthorCliCommandNode();

        return new CliEntryPoint(authService, authCliCommandNode, userCliCommandNode, authorCliCommandNode);
    }
}
