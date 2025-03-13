package com.esgi.domain.loans.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.LoanModel;
import com.esgi.data.loans.LoanRepository;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanMapper;
import com.esgi.domain.loans.LoanService;

import java.util.List;

public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private LoanMapper loanMapper;

    public void createLoan(LoanEntity loanEntity) throws ConstraintViolationException, NotFoundException {
        LoanModel loanModel = loanMapper.entityToModel(loanEntity);
        loanRepository.create(loanModel);
    }

    public void bookReturn(LoanEntity loanEntity) throws ConstraintViolationException, NotFoundException {
        LoanModel loanModel = loanMapper.entityToModel(loanEntity);
        loanRepository.bookReturn(loanModel);
    }

    public LoanEntity getLoan(int id) throws NotFoundException {
        LoanModel loanModel = loanRepository.getById(id);
        return loanMapper.modelToEntity(loanModel);
    }

    public List<LoanEntity> getAll() {
        var loanModel = loanRepository.getAll();
        return this.loanMapper.modelToEntity(loanModel);
    }

    public List<LoanEntity> getByUserId(int userId) throws ConstraintViolationException, NotFoundException {
        var loanModel = loanRepository.getByUserId(userId);
        return this.loanMapper.modelToEntity(loanModel);
    }

    public List<LoanEntity> getAllLoansBook() throws ConstraintViolationException, NotFoundException {
        var loanModel = loanRepository.getAllLoansBook();
        return this.loanMapper.modelToEntity(loanModel);
    }

}
