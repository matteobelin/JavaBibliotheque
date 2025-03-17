package com.esgi.data.loans;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface LoanRepository {
    LoanModel getById(Integer id) throws NotFoundException;
    LoanModel findLoanByUserIdAndBookId(Integer userId, Integer bookId) throws NotFoundException;
    List<LoanModel> getAll() ;
    List<LoanModel> getByUserId(Integer userId) throws ConstraintViolationException, NotFoundException;
    List<LoanModel> getCurrentLoanOfUser(Integer userId) ;
    void create(LoanModel loanModel) throws ConstraintViolationException, NotFoundException, BookLoanException;
    void bookReturn(LoanModel loanModel) throws ConstraintViolationException, NotFoundException, BookLoanException;
    boolean isBookBorrowed(Integer bookId);
    List<LoanModel> getCurrentLoan();
}
