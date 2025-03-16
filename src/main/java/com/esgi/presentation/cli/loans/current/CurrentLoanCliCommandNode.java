package com.esgi.presentation.cli.loans.current;

import com.esgi.core.exceptions.ActionDeniedException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.LoanUtils;

import java.util.List;

public class CurrentLoanCliCommandNode extends CliCommandNode {

    public static final String NAME = "currents";

    public static final String DESCRIPTION = "Returns the current loans of a user";

    private final AuthService authService;
    private final UserService userService;
    private final LoanService loanService;

    public CurrentLoanCliCommandNode(AuthService authService, UserService userService, LoanService loanService) {
        super(NAME, DESCRIPTION);
        this.authService = authService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @Override
    public ExitCode run(String[] args) {
        var values = this.extractValuesFromArgs(args);

        try {
            var user = this.getUser(values);

            var userLoans = this.loanService.getCurrentLoanOfUser(user.getId());

            var loanTable = LoanUtils.makeLoanTable(userLoans);

            AppLogger.info("Current loans of user '%s' (%s) : ".formatted(user.getName(), user.getEmail()));
            AppLogger.emptyLine();
            AppLogger.info(loanTable);

        } catch (NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (ActionDeniedException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ACTION_DENIED;
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }

    private UserEntity getUser(List<String> values) throws NotFoundException, ActionDeniedException, InternalErrorException {
        if (values.isEmpty()) {
            return this.authService.getLoggedInUser();
        }

        if (authService.isLoggedInUserAdmin()) {
            var email = values.get(0);
            return this.userService.getUserByEmail(email);
        }

        throw new ActionDeniedException("Only admin can see the current loans of another user");
    }
}
