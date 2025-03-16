package com.esgi.presentation.cli.auth.signin;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.AppLoggerColorEnum;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.util.List;

public class SigninCliCommandNode extends CliCommandNode {
    public static final String NAME = "signin";
    public static final String DESCRIPTION = "Creates a new user account.";

    private final UserService userService;

    public SigninCliCommandNode(UserService userService) {
        super(NAME, DESCRIPTION);
        this.userService = userService;
    }

    @Override
    public ExitCode run(String[] args) throws InternalErrorException {
        List<String> values = this.extractValuesFromArgs(args);

        if (values.size() < 2) {
            var errorMessage = String.format("The '%s' command requires at least two arguments : EMAIL and PASSWORD", NAME);
            AppLogger.error(errorMessage);
            return ExitCode.ARGUMENT_MISSING;
        }

        var user = this.makeUserEntity(values);

        try {
            this.userService.createUser(user);

            String successMessage = "Welcome to Biblio %s, your account has been created !".formatted(user.getName());

            String loginCommand = "biblio auth login %s %s --save".formatted(user.getEmail(), user.getPassword());
            String loginMessage = "-> You can now login using the command : %s".formatted(loginCommand);

            List<String> messageLines = StringUtils.wrapInLargeBox(List.of(
                    successMessage,
                    loginMessage
            ));

            AppLogger.writeLines(AppLoggerColorEnum.GREEN, messageLines);
        } catch (ConstraintViolationException | InvalidArgumentException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }

    private UserEntity makeUserEntity(List<String> values) {
        var user = new UserEntity();
        user.setEmail(values.get(0));
        user.setPassword(values.get(1));

        if (values.size() > 2) {
            user.setName(values.get(2));
        }

        return user;
    }
}
