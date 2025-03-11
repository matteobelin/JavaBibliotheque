package com.esgi.presentation.cli.auth.login;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.domain.auth.AuthCredentials;
import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;

import java.util.List;

public class LoginCliCommandNode extends CliCommandNode {
    public static final String NAME = "login";
    public static final String DESCRIPTION =
            "Login into the system using email and password (biblio auth login EMAIL PASSWORD)";

    private final AuthService authService;

    public LoginCliCommandNode(AuthService authService) {
        super(NAME, DESCRIPTION);

        this.authService = authService;
    }

    @Override
    public ExitCode run(String[] args) {
        var values = this.extractValuesFromArgs(args);
        if (values.size() < 2) {
            var errorMessage = String.format("The '%s' command requires 2 arguments : EMAIL and PASSWORD", NAME);
            AppLogger.error(errorMessage);
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            var credentials = makeCredentials(values);

            var connectedUser = this.authService.login(credentials);

            AppLogger.success("Welcome back %s !".formatted(connectedUser.getName()));
        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            return mapExceptionToExitCode(e);
        }

        return ExitCode.OK;
    }


    private AuthCredentials makeCredentials(List<String> values) {
        String email = values.get(0);
        String password = values.get(1);
        boolean saveCredentials = true; // in CLI it needs to be true, otherwise the user can't do anything

        return new AuthCredentials(email, password, saveCredentials);
    }

    private boolean isSaveCredentialsOption(CliCommandNodeOption option) {
        return option instanceof SaveCredentialsCommandOption;
    }

    private ExitCode mapExceptionToExitCode(Exception e) {
        if (e instanceof IncorrectCredentialsException) {
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.INTERNAL_ERROR;
    }
}
