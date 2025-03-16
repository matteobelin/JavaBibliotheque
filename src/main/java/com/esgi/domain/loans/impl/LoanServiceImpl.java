package com.esgi.domain.loans.impl;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.LoanModel;
import com.esgi.data.loans.LoanRepository;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanMapper;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.loans.validator.LoanValidator;

import java.util.List;

public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanValidator loanValidator;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper, LoanValidator validator) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        loanValidator = validator;
    }

    public void createLoan(LoanEntity loanEntity) throws ConstraintViolationException, NotFoundException, BookLoanException {
        boolean bookCanNotBeBorrowed = !loanValidator.canBookBeBorrowed(loanEntity.getBook());
        if(bookCanNotBeBorrowed) {
            throw new BookLoanException("This book can not be borrowed");
        }

        LoanModel loanModel = loanMapper.entityToModel(loanEntity);
        loanRepository.create(loanModel);
    }

    public void bookReturn(LoanEntity loanEntity) throws ConstraintViolationException, NotFoundException, BookLoanException {
        boolean bookCanNotBeReturned = !loanValidator.canBookBeReturned(loanEntity.getBook());
        if(bookCanNotBeReturned) {
            throw new BookLoanException("This book can not be returned");
        }

        LoanModel loanModel = loanMapper.entityToModel(loanEntity);
        loanRepository.bookReturn(loanModel);
    }

    public LoanEntity getLoan(int id) throws NotFoundException, InternalErrorException {
        LoanModel loanModel = loanRepository.getById(id);
        return loanMapper.modelToEntity(loanModel);
    }

    public LoanEntity findLoanByUserIdAndBookId(Integer userId, Integer bookId)
            throws NotFoundException, InternalErrorException {
        var model = this.loanRepository.findLoanByUserIdAndBookId(userId, bookId);
        return loanMapper.modelToEntity(model);
    }

    public List<LoanEntity> getAll() throws NotFoundException, InternalErrorException {
        var loanModel = loanRepository.getAll();
        return this.loanMapper.modelToEntity(loanModel);
    }

    public List<LoanEntity> getCurrentLoan()throws NotFoundException, InternalErrorException {
        var loanModel = loanRepository.getCurrentLoan();
        return this.loanMapper.modelToEntity(loanModel);
    }

    public List<LoanEntity> getByUserId(int userId) throws ConstraintViolationException, NotFoundException, InternalErrorException {
        var loanModel = loanRepository.getByUserId(userId);
        return this.loanMapper.modelToEntity(loanModel);
    }

    @Override
    public List<LoanEntity> getCurrentLoanOfUser(int userId) throws NotFoundException, InternalErrorException {
        var models = loanRepository.getCurrentLoanOfUser(userId);
        return this.loanMapper.modelToEntity(models);
    }
}
