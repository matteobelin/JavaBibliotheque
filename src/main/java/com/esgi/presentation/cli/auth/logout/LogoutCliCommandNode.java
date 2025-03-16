package com.esgi.presentation.cli.auth.logout;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class LogoutCliCommandNode extends CliCommandNode {
    public static final String NAME = "logout";
    public static final String DESCRIPTION = "Logs out the connected user";

    private final AuthService authService;

    public LogoutCliCommandNode(AuthService authService) {
        super(NAME, DESCRIPTION);
        this.authService = authService;
    }

    @Override
    public ExitCode run(String[] args) {
        if (args.length > 0) {
            AppLogger.warn("This command doesn't take any arguments. They will be ignored.");
        }

        boolean isNotLoggedIn = !this.authService.isLoggedIn();
        if (isNotLoggedIn) {
            AppLogger.warn("No user logged in.");
            return ExitCode.OK;
        }

        try {
            this.authService.logout();
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }
}
