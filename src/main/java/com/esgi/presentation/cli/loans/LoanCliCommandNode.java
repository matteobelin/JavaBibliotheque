package com.esgi.presentation.cli.loans;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.loans.add.AddLoanCliCommandNode;
import com.esgi.presentation.cli.loans.current.CurrentLoanCliCommandNode;
import com.esgi.presentation.cli.loans.history.LoanHistoryCliCommandNode;
import com.esgi.presentation.cli.loans.returning.ReturnBookCliCommandNode;

import java.util.List;

public class LoanCliCommandNode extends CliCommandNode {

    public static final String NAME = "loans";

    public static final String DESCRIPTION = "Manages both current and past user's book loans";

    public LoanCliCommandNode(
            AuthService authService,
            AddLoanCliCommandNode addLoanCommand,
            ReturnBookCliCommandNode returnBookCommand,
            LoanHistoryCliCommandNode loanHistoryCommand,
            CurrentLoanCliCommandNode currentLoanCommand
    ) {
        super(NAME, DESCRIPTION);

        if (authService.isLoggedIn()) {
            this.childrenCommands.add(addLoanCommand);
            this.childrenCommands.add(returnBookCommand);
            this.childrenCommands.add(loanHistoryCommand);
            this.childrenCommands.add(currentLoanCommand);
        }

        var helpCommand = new HelpCliCommand(List.copyOf(this.getChildrenCommands()));
        this.childrenCommands.add(helpCommand);
    }
}
