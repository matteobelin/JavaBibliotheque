package com.esgi.presentation.cli.auth.login;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.domain.auth.AuthCredentials;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.utils.ArgsParserUtils;

import java.util.List;

public class LoginCliCommandNode extends CliCommandNode {
    public static final String NAME = "login";
    public static final String DESCRIPTION =
            "Login into the system using email and password (biblio auth login EMAIL PASSWORD [OPTIONS])";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.USER;

    public LoginCliCommandNode() {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        var saveCredentialsOptions = new SaveCredentialsCommandOption();
        commandOptions.add(saveCredentialsOptions);
    }

    @Override
    public ExitCode run(String[] args) {
        var values = ArgsParserUtils.extractValuesFromArgs(args, this.getCommandOptions());
        if (values.size() < 2) {
            var errorMessage = String.format("The '%s' command requires at least two arguments : EMAIL and PASSWORD", NAME);
            AppLogger.error(errorMessage);
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            var options = this.extractOptionsFromArgs(args);
            var credentials = makeCredentials(values, options);

            var authService = AuthServiceFactory.getAuthService();
            var connectedUser = authService.login(credentials);

            AppLogger.success("Welcome %s !".formatted(connectedUser.getName()));
        } catch (Exception e) {
            AppLogger.error(e.getMessage());
            return mapExceptionToExitCode(e);
        }

        return ExitCode.OK;
    }


    private AuthCredentials makeCredentials(List<String> values, List<CliCommandNodeOption> options) {
        String email = values.get(0);
        String password = values.get(1);
        boolean saveCredentials = options.stream().anyMatch(this::isSaveCredentialsOption);

        return new AuthCredentials(email, password, saveCredentials);
    }

    private boolean isSaveCredentialsOption(CliCommandNodeOption option) {
        return option instanceof SaveCredentialsCommandOption;
    }

    private ExitCode mapExceptionToExitCode(Exception e) {
        if (e instanceof OptionRequiresValueException) {
            return ExitCode.ARGUMENT_MISSING;
        } else if (e instanceof IncorrectCredentialsException) {
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.INTERNAL_ERROR;
    }
}
