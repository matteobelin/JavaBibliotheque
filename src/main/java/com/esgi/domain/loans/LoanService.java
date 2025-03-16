package com.esgi.domain.loans;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface LoanService {
    void createLoan(LoanEntity loan) throws ConstraintViolationException, NotFoundException, BookLoanException;;
    void bookReturn(LoanEntity loan) throws ConstraintViolationException, NotFoundException, BookLoanException;
    LoanEntity getLoan(int id) throws NotFoundException, InternalErrorException;
    LoanEntity findLoanByUserIdAndBookId(Integer userId, Integer bookId) throws NotFoundException, InternalErrorException;
    List<LoanEntity> getAll() throws NotFoundException, InternalErrorException;
    List<LoanEntity> getAllCurrents() throws NotFoundException, InternalErrorException;
    List<LoanEntity> getByUserId(int id) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    List<LoanEntity> getCurrentLoanOfUser(int userId) throws NotFoundException, InternalErrorException;
}
