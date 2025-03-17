package com.esgi.presentation.cli.users.edit;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.options.AdminCommandOption;
import com.esgi.presentation.cli.options.ValueCliCommandNodeOption;
import com.esgi.presentation.utils.StringUtils;

import java.util.List;

public class EditUserCliCommandNode extends CliCommandNode {
    public static final String NAME = "edit";
    public static final String DESCRIPTION = "Updates a user information. The email argument is only available for ADMINS, in other case, the logged user's email will be used for the command";

    public static final String EMAIL_OPTION_NAME = "email";
    public static final String EMAIL_OPTION_SHORT_NAME = "e";
    public static final String EMAIL_OPTION_DESCRIPTION = "Specify the new email";

    public static final String NAME_OPTION_NAME = "name";
    public static final String NAME_OPTION_SHORT_NAME = "n";
    public static final String NAME_OPTION_DESCRIPTION = "Specify the new name";

    public static final String ADMIN_OPTION_DESCRIPTION = "Specify if the user is admin. [y/n]";

    private final AuthService authService;
    private final UserService userService;

    protected EditUserCliCommandNode(AuthService authService, UserService userService) {
        super(NAME, DESCRIPTION);

        this.authService = authService;
        this.userService = userService;

        var emailValueOption = new ValueCliCommandNodeOption(EMAIL_OPTION_NAME, EMAIL_OPTION_SHORT_NAME,
                EMAIL_OPTION_DESCRIPTION);
        var nameValueOption = new ValueCliCommandNodeOption(NAME_OPTION_NAME, NAME_OPTION_SHORT_NAME,
                NAME_OPTION_DESCRIPTION);

        this.commandOptions.add(emailValueOption);
        this.commandOptions.add(nameValueOption);

        if (authService.isLoggedInUserAdmin()) {
            var adminOption = new AdminCommandOption(ADMIN_OPTION_DESCRIPTION, true);
            this.commandOptions.add(adminOption);
        }
    }

    @Override
    public ExitCode run(String[] args) {
        boolean isNotLoggedIn = !this.authService.isLoggedIn();
        if (isNotLoggedIn) {
            AppLogger.error("This command is only available to logged users");
            return ExitCode.ACTION_DENIED;
        }

        var values = this.extractValuesFromArgs(args);

        boolean valuesIsNotEmpty = !values.isEmpty();
        boolean isNotAdmin = !this.authService.isLoggedInUserAdmin();
        if (valuesIsNotEmpty && isNotAdmin) {
            AppLogger.error("Only admin users have the right to edit other user's information");
            return ExitCode.ACTION_DENIED;
        }

        String emailOfUserToUpdate = valuesIsNotEmpty
                ? values.get(0)
                : this.authService.getLoggedInUser().getEmail();

        try {

            var originalUser = this.userService.getUserByEmail(emailOfUserToUpdate);

            var options = this.extractOptionsFromArgs(args);
            var user = this.makeUserEntityForUpdate(originalUser, options);

            this.userService.updateUser(user);
            AppLogger.success("User updated successfully !");

        } catch (OptionRequiresValueException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_MISSING;
        } catch (NotFoundException | InvalidArgumentException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }



    private UserEntity makeUserEntityForUpdate(UserEntity originalUser, List<CliCommandNodeOption> options) {
        var updatedUserInformation = new UserEntity();
        updatedUserInformation.setId(originalUser.getId());

        var emailOption = options.stream().filter(this::isEmailOption).findFirst();
        if(emailOption.isPresent()) {
            updatedUserInformation.setEmail(emailOption.get().getValue());
        } else {
            updatedUserInformation.setEmail(originalUser.getEmail());
        }

        var nameOption = options.stream().filter(this::isNameOption).findFirst();
        if(nameOption.isPresent()) {
            updatedUserInformation.setName(nameOption.get().getValue());
        } else {
            updatedUserInformation.setName(originalUser.getEmail());
        }

        var adminOption = options.stream().filter(this::isAdminOption).findFirst();
        if(adminOption.isPresent()) {
            String isAdminStringValue = adminOption.get().getValue();
            boolean isAdmin = StringUtils.yesNoValueToBoolean(isAdminStringValue);
            updatedUserInformation.setAdmin(isAdmin);

            return updatedUserInformation;
        }

        updatedUserInformation.setAdmin(originalUser.isAdmin());
        return updatedUserInformation;
    }

    private boolean isEmailOption(CliCommandNodeOption option) {
        return option.getName().equals(EMAIL_OPTION_NAME) &&
               option.getShortName().equals(EMAIL_OPTION_SHORT_NAME) &&
               option.getDescription().equals(EMAIL_OPTION_DESCRIPTION);
    }

    private boolean isNameOption(CliCommandNodeOption option) {
        return option.getName().equals(NAME_OPTION_NAME) &&
               option.getShortName().equals(NAME_OPTION_SHORT_NAME) &&
               option.getDescription().equals(NAME_OPTION_DESCRIPTION);
    }

    private boolean isAdminOption(CliCommandNodeOption option) {
        return option.getName().equals(AdminCommandOption.NAME) &&
               option.getShortName().equals(AdminCommandOption.SHORT_NAME) &&
               option.getDescription().equals(ADMIN_OPTION_DESCRIPTION);
    }
}
