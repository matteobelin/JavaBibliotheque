package com.esgi.presentation.cli.loans.add;

import com.esgi.core.exceptions.ActionDeniedException;
import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.LoanUtils;

import java.util.List;

public class AddLoanCliCommandNode extends CliCommandNode {

    public static final String NAME = "add";

    public static final String DESCRIPTION = "Allows a user to borrow a book.";

    private final AuthService authService;

    private final UserService userService;

    private final LoanService loanService;

    public AddLoanCliCommandNode(AuthService authService, UserService userService, LoanService loanService) {
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
            var loan = this.makeLoan(values);

            this.loanService.createLoan(loan);

            AppLogger.success("The book has been borrowed !");
        } catch (NumberFormatException e) {
            AppLogger.error("This command requires a valid book ID (number)");
            return ExitCode.ARGUMENT_INVALID;
        } catch (ConstraintViolationException | NotFoundException | ActionDeniedException | BookLoanException | InternalErrorException e) {
            return LoanUtils.handleBookLoanCommandException(e);
        }

        return ExitCode.OK;
    }

    private LoanEntity makeLoan(List<String> values) throws NumberFormatException, NotFoundException, ActionDeniedException, InternalErrorException {
        var loan = new LoanEntity();

        String bookStringId = values.get(0);
        var bookId = Integer.parseInt(bookStringId);
        var book = new BookEntity();
        book.setId(bookId);
        loan.setBook(book);

        if (values.size() == 1) {
            loan.setUserId(authService.getLoggedInUser().getId());
        } else if (authService.isLoggedInUserAdmin()) {
            var email = values.get(1);
            var userBorrowingBook = userService.getUserByEmail(email);
            loan.setUserId(userBorrowingBook.getId());
        } else {
            throw new ActionDeniedException("Only admins can borrows books on behalf of another user");
        }

        return loan;
    }
}
