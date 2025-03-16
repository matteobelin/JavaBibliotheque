package com.esgi.presentation.cli.users.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.options.AdminCommandOption;
import com.esgi.presentation.cli.utils.ArgsParserUtils;

import java.util.List;

public class AddUserCliCommandNode extends CliCommandNode {
    public static final String NAME = "add";
    public static final String DESCRIPTION = "Registers a new user in the system.";

    public static final String ADMIN_OPTION_DESCRIPTION = "Create the user with the admin role.";

    private final UserService userService;

    public AddUserCliCommandNode(UserService userService) {
        super(NAME, DESCRIPTION);

        this.userService = userService;

        var adminOption = new AdminCommandOption(ADMIN_OPTION_DESCRIPTION);
        commandOptions.add(adminOption);
    }

    @Override
    public ExitCode run(String[] args) throws InternalErrorException {
        List<String> values = ArgsParserUtils.extractValuesFromArgs(args, this.getCommandOptions());
        if (values.size() < 2) {
            AppLogger.error("The email and password are required : users add [OPTIONS] EMAIL PASSWORD");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            List<CliCommandNodeOption> options = this.extractOptionsFromArgs(args);

            UserEntity user = this.createUserEntity(values, options);
            this.userService.createUser(user);

            String successMessage = String.format("User with email '%s' created !", user.getEmail());
            AppLogger.success(successMessage);

            return ExitCode.OK;
        } catch (OptionRequiresValueException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_MISSING;
        } catch (ConstraintViolationException | InvalidArgumentException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }
    }

    private UserEntity createUserEntity(List<String> values, List<CliCommandNodeOption> options) {
        UserEntity user = new UserEntity();
        user.setEmail(values.get(0));
        user.setPassword(values.get(1));

        boolean isAdminOptionUsed = options.stream().anyMatch(this::isAdminOption);
        user.setAdmin(isAdminOptionUsed);

        return user;
    }

    private boolean isAdminOption(CliCommandNodeOption option) {
        return AdminCommandOption.NAME.equals(option.getName());
    }
}
