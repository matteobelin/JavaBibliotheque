package com.esgi.presentation.cli.users.delete;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class DeleteUserCliCommandNode extends CliCommandNode {
    public static final String NAME = "delete";
    public static final String DESCRIPTION = "Removes a user from the system. The email argument is only available for ADMINS, in other case, the current logged user's email will be used for the command.";

    private final AuthService authService;
    private final UserService userService;

    public DeleteUserCliCommandNode(AuthService authService, UserService userService) {
        super(NAME, DESCRIPTION);
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public ExitCode run(String[] args) {
        boolean isNotLoggedIn = !this.authService.isLoggedIn();
        if (isNotLoggedIn) {
            AppLogger.error("Only logged users can use this command.");
            return ExitCode.ACTION_DENIED;
        }

        var values = this.extractValuesFromArgs(args);

        boolean valuesIsNotEmpty = !values.isEmpty();
        boolean isNotAdmin = !this.authService.isLoggedInUserAdmin();
        if (valuesIsNotEmpty && isNotAdmin) {
            AppLogger.error("Only admins can delete other users");
            return ExitCode.ACTION_DENIED;
        }

        String email = valuesIsNotEmpty
                ? values.get(0)
                : this.authService.getLoggedInUser().getEmail();

        try {
            var userToDelete = this.userService.getUserByEmail(email);

            this.userService.deleteUser(userToDelete.getId());

            AppLogger.success("User with email '%s' has been deleted !".formatted(email));
        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }
}
