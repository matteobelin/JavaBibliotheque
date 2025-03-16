package com.esgi.presentation.cli.loans;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.loans.LoanServiceFactory;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.cli.loans.add.AddLoanCliCommandNode;
import com.esgi.presentation.cli.loans.current.CurrentLoanCliCommandNode;
import com.esgi.presentation.cli.loans.history.LoanHistoryCliCommandNode;
import com.esgi.presentation.cli.loans.returning.ReturnBookCliCommandNode;

public final class LoanCliCommandFactory {

    public static LoanCliCommandNode makeLoanCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();
        var userService = UserServiceFactory.getUserService();
        var loanService = LoanServiceFactory.getLoanService();

        var addLoanCommand = new AddLoanCliCommandNode(authService, userService, loanService);
        var returnBookCommand = new ReturnBookCliCommandNode(authService, userService, loanService);
        var loanHistoryCommand = new LoanHistoryCliCommandNode(authService, userService, loanService);
        var currentLoanCommand = new CurrentLoanCliCommandNode(authService, userService, loanService);

        return new LoanCliCommandNode(authService, addLoanCommand, returnBookCommand, loanHistoryCommand, currentLoanCommand);
    }
}
