package com.esgi.presentation.cli.loans.history;

import com.esgi.core.exceptions.ActionDeniedException;
import com.esgi.core.exceptions.ConstraintViolationException;
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

public class LoanHistoryCliCommandNode extends CliCommandNode {

    public static final String NAME = "history";

    public static final String DESCRIPTION = "Displays the loan history of a user.";

    private final AuthService authService;
    private final UserService userService;
    private final LoanService loanService;

    public LoanHistoryCliCommandNode(AuthService authService, UserService userService, LoanService loanService) {
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
            var loanHistory = this.loanService.getByUserId(user.getId());

            var loanHistoryTable = LoanUtils.makeLoanTable(loanHistory);

            AppLogger.info("Loan history of '%s' (%s) : ".formatted(user.getName(), user.getEmail()));
            AppLogger.emptyLine();
            AppLogger.info(loanHistoryTable);

        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (ActionDeniedException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ACTION_DENIED;
        }

        return ExitCode.OK;
    }

    private UserEntity getUser(List<String> values) throws NotFoundException, ActionDeniedException {
        if (values.isEmpty()) {
            return this.authService.getLoggedInUser();
        }

        if (authService.isLoggedInUserAdmin()) {
            var email = values.get(0);
            return this.userService.getUserByEmail(email);
        }

        throw new ActionDeniedException("Only admin can see the loan history of another user");
    }
}
