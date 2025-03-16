package com.esgi.domain.loans;

import com.esgi.data.loans.LoanRepositoryFactory;
import com.esgi.domain.books.BookServiceFactory;
import com.esgi.domain.loans.impl.LoanServiceImpl;
import com.esgi.domain.loans.validator.LoanValidator;

public final class LoanServiceFactory {

    private static LoanService loanService;

    public static LoanService getLoanService() {
        if (loanService == null) {
            loanService = makeLoanService();
        }
        return loanService;
    }

    public static LoanService makeLoanService() {
        var loanRepository = LoanRepositoryFactory.makeLoanRepository();

        var bookService = BookServiceFactory.getBookService();
        var loanMapper = new LoanMapper(bookService);
        var loanValidator = new LoanValidator(loanRepository, bookService);

        return new LoanServiceImpl(loanRepository, loanMapper, loanValidator);
    }
}
