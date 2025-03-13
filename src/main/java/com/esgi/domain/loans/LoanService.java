package com.esgi.domain.loans;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface LoanService {
    void createLoan(LoanEntity loan) throws ConstraintViolationException, NotFoundException;;
    void bookReturn(LoanEntity loan) throws ConstraintViolationException, NotFoundException;
    LoanEntity getLoan(int id) throws NotFoundException;
    List<LoanEntity> getAll();
    List<LoanEntity> getByUserId(int id) throws ConstraintViolationException, NotFoundException;;
    List<LoanEntity> getAllLoansBook() throws ConstraintViolationException, NotFoundException;
}
