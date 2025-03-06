package com.esgi.domain.books.impl;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookMapper;
import com.esgi.domain.books.BookService;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookEntity getBookById(int id) throws NotFoundException, NotFoundException {
        BookModel bookModel = bookRepository.getById(id);
            return bookMapper.modelToEntity(bookModel);
    }
}
