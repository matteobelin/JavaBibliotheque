package com.esgi.domain.loans;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface LoanService {
    void createLoan(LoanEntity loan) throws ConstraintViolationException, NotFoundException, BookLoanException;;
    void bookReturn(LoanEntity loan) throws ConstraintViolationException, NotFoundException, BookLoanException;
    LoanEntity getLoan(int id) throws NotFoundException;
    LoanEntity findLoanByUserIdAndBookId(Integer userId, Integer bookId) throws NotFoundException;
    List<LoanEntity> getAll() throws NotFoundException;
    List<LoanEntity> getByUserId(int id) throws ConstraintViolationException, NotFoundException;
    List<LoanEntity> getCurrentLoanOfUser(int userId) throws NotFoundException;
    List<LoanEntity> getCurrentLoan() throws NotFoundException;
}
