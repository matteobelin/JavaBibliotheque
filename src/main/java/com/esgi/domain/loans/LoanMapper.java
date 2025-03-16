package com.esgi.domain.loans;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.LoanModel;
import com.esgi.domain.books.BookService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LoanMapper {
    private final BookService bookService;

    public LoanMapper(BookService bookService) {
        this.bookService = bookService;
    }

    public LoanEntity modelToEntity(LoanModel loan) throws NotFoundException, InternalErrorException {
        var book = this.bookService.getBookById(loan.getBookId());
        return new LoanEntity(
                loan.getId(),
                loan.getUserId(),
                book,
                loan.getStartDate(),
                loan.getEndDate()
        );
    }

    public LoanModel entityToModel(LoanEntity loan){
        return new LoanModel(
                loan.getId(),
                loan.getUserId(),
                loan.getBook().getId(),
                (Date) loan.getStartDate(),
                (Date) loan.getEndDate()
        );
    }

    public List<LoanEntity> modelToEntity(List<LoanModel> loans) throws NotFoundException, InternalErrorException {
        List<LoanEntity> loansEntity = new ArrayList<>();
        for(LoanModel loan : loans){
            loansEntity.add(this.modelToEntity(loan));
        }
        return loansEntity;
    }
}
