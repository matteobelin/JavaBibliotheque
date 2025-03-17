package com.esgi.presentation.cli.loans.returning;

import com.esgi.core.exceptions.ActionDeniedException;
import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.LoanUtils;

import java.util.List;

public class ReturnBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "return";

    public static final String DESCRIPTION = "Allows a user to return a borrowed book.";

    private final AuthService authService;

    private final UserService userService;

    private final LoanService loanService;

    public ReturnBookCliCommandNode(AuthService authService, UserService userService, LoanService loanService) {
        super(NAME, DESCRIPTION);
        this.authService = authService;
        this.userService = userService;
        this.loanService = loanService;
    }


    @Override
    public ExitCode run(String[] args) {

        var values = this.extractValuesFromArgs(args);
        if (values.isEmpty()) {
            AppLogger.error("This command requires 1 argument : BOOK_ID");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            var loan = this.getLoan(values);

            this.loanService.bookReturn(loan);

            AppLogger.success("The book has been returned.");

        } catch (NumberFormatException e) {
            AppLogger.error("This command requires a valid book ID (number)");
            return ExitCode.ARGUMENT_INVALID;
        } catch (ConstraintViolationException | NotFoundException |
                 ActionDeniedException | BookLoanException e) {
            return LoanUtils.handleBookLoanCommandException(e);
        }

        return ExitCode.OK;
    }

    private LoanEntity getLoan(List<String> values)
            throws NumberFormatException, NotFoundException, ActionDeniedException {
        String bookStringId = values.get(0);
        var bookId = Integer.parseInt(bookStringId);
        var userId = this.getUserId(values);

        return this.loanService.findLoanByUserIdAndBookId(userId, bookId);
    }

    private Integer getUserId(List<String> values) throws NotFoundException, ActionDeniedException {
        if (values.size() == 1) {
            return authService.getLoggedInUser().getId();
        }

        if (authService.isLoggedInUserAdmin()) {
            var email = values.get(1);
            var userBorrowingBook = userService.getUserByEmail(email);
            return userBorrowingBook.getId();
        }

        throw new ActionDeniedException("Only admins can borrows books on behalf of another user");
    }
}
