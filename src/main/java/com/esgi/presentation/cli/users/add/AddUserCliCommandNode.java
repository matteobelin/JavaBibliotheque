package com.esgi.presentation.cli.users.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.options.AdminCommandOption;

import java.util.List;

public class AddUserCliCommandNode extends CliCommandNode {
    public static final String NAME = "add";

    private static final CliCommandNodeOption AdminOption =
            new AdminCommandOption("Create the user with the admin role.");

    private final UserService userService;

    public AddUserCliCommandNode(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Add a user to the system with email and password (ex: users add [OPTIONS] EMAIL PASSWORD)";
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return List.of();
    }

    @Override
    public List<CliCommandNodeOption> getCommandOptions() {
        return List.of(AdminOption);
    }

    @Override
    public ExitCode run(String[] args) {
        List<String> values = this.extractValuesFromArgs(args);
        if (values.size() < 2) {
            AppLogger.error("The email and password are required : users add [OPTIONS] EMAIL PASSWORD");
            return ExitCode.ARGUMENT_MISSING;
        }

        List<CliCommandNodeOption> options = this.extractOptionsFromArgs(args);
        UserEntity user = this.createUserEntity(values, options);

        try {
            this.userService.createUser(user);

            String successMessage = String.format("User with email '%s' created !", user.getEmail());
            AppLogger.success(successMessage);

            return ExitCode.OK;
        } catch (ConstraintViolationException | InvalidArgumentException e) {
            AppLogger.error(e.getMessage());

            return ExitCode.ARGUMENT_INVALID;
        }
    }

    private UserEntity createUserEntity(List<String> values, List<CliCommandNodeOption> options) {
        UserEntity user = new UserEntity();
        user.setEmail(values.get(0));
        user.setPassword(values.get(1));
        user.setAdmin(options.contains(AdminOption));
        return user;
    }
}
