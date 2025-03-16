package com.esgi.data.loans;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface LoanRepository {
    LoanModel getById(Integer id) throws NotFoundException, InternalErrorException;
    LoanModel findLoanByUserIdAndBookId(Integer userId, Integer bookId) throws NotFoundException, InternalErrorException;
    List<LoanModel> getAll();
    List<LoanModel> getByUserId(Integer userId) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    List<LoanModel> getCurrentLoanOfUser(Integer userId) throws InternalErrorException;
    void create(LoanModel loanModel) throws ConstraintViolationException, NotFoundException, BookLoanException;
    void bookReturn(LoanModel loanModel) throws ConstraintViolationException, NotFoundException, BookLoanException;
    boolean isBookBorrowed(Integer bookId);
    List<LoanModel> getCurrentLoan()throws InternalErrorException;
}
