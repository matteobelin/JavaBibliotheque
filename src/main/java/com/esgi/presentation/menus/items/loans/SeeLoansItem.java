package com.esgi.presentation.menus.items.loans;

import com.esgi.domain.AppContext;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.loans.LoanServiceFactory;
import com.esgi.domain.users.UserEntity;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;
import com.esgi.presentation.utils.LoanUtils;

import java.util.List;

public class SeeLoansItem implements MenuItem {
    @Override
    public String getTitle() {
        return "see current loans";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        UserEntity user = AppContext.getInstance().getLoggedInUser();

        LoanService loanService = LoanServiceFactory.getLoanService();
        List<LoanEntity> currentLoans = loanService.getCurrentLoanOfUser(user.getId());

        AppLogger.info("Current loans of user '%s' (%s) : ".formatted(user.getName(), user.getEmail()));
        AppLogger.emptyLine();
        AppLogger.info(LoanUtils.makeLoanTable(currentLoans));


        awaitUserInput(menu);
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
