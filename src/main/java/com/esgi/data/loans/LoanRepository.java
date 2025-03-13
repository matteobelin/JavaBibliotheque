package com.esgi.data.loans;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import java.util.List;

public interface LoanRepository {
    LoanModel getById(Integer id) throws NotFoundException;
    void create(LoanModel loanModel) throws ConstraintViolationException, NotFoundException;
    void bookReturn(LoanModel loanModel) throws ConstraintViolationException, NotFoundException;
    List<LoanModel> getAll();
    List<LoanModel> getByUserId(Integer userId) throws ConstraintViolationException, NotFoundException;
    List<LoanModel> getAllLoansBook() throws ConstraintViolationException, NotFoundException;
}
