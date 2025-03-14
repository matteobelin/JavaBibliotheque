package com.esgi.data.loans;

import com.esgi.data.loans.impl.LoanRepositoryImpl;

public class LoanRepositoryFactory {
    public static LoanRepository makeLoanRepository() {return new LoanRepositoryImpl();}
}
