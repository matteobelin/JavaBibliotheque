package com.esgi.domain.loans.validator;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.LoanRepository;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;

public final class LoanValidator {

    private final LoanRepository loanRepository;

    private final BookService bookService;

    public LoanValidator(LoanRepository loanRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
    }

    public boolean canBookBeBorrowed(BookEntity bookEntity) {
        int bookId = bookEntity.getId();
        return bookWithIdExist(bookId) && !this.loanRepository.isBookBorrowed(bookId);
    }

    public boolean canBookBeReturned(BookEntity bookEntity) {
        int bookId = bookEntity.getId();
        return this.loanRepository.isBookBorrowed(bookId);
    }

    private boolean bookWithIdExist(Integer bookId) {
        try {
            this.bookService.getBookById(bookId);
            return true;
        } catch (NotFoundException | InternalErrorException e) {
            return false;
        }
    }
}
