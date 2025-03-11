package com.esgi.domain.books.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookMapper;
import com.esgi.domain.books.BookService;
import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookEntity getBookById(int id) throws  NotFoundException {
        BookModel bookModel = bookRepository.getById(id);
            return bookMapper.modelToEntity(bookModel);
    }

    @Override
    public List<BookEntity> getBooksByGenre (int genreId) throws NotFoundException{
        List<BookModel> books = bookRepository.getByGenre(genreId);
        return convertToEntities(books);
    }

    @Override
    public List<BookEntity> getBooksByAuthor(int authorId) throws NotFoundException {
        List<BookModel> books = bookRepository.getByAuthor(authorId);
        return convertToEntities(books);
    }

    @Override
    public List<BookEntity> getBooksByTitle(String name) throws NotFoundException {
        List<BookModel> books = bookRepository.getByTitle(name);
        return convertToEntities(books);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        List<BookModel> books = bookRepository.getAllBook();
        return convertToEntities(books);
    }

    private List<BookEntity> convertToEntities(List<BookModel> books) {
        List<BookEntity> bookEntities = new ArrayList<>();
        for (BookModel bookModel : books) {
            bookEntities.add(bookMapper.modelToEntity(bookModel));
        }
        return bookEntities;
    }

    @Override
    public void createBook(BookEntity bookEntity) throws ConstraintViolationException, NotFoundException {
        BookModel bookModel = bookMapper.entityToModel(bookEntity);
        bookRepository.create(bookModel);
    }

    @Override
    public void updateBook(BookEntity bookEntity) throws ConstraintViolationException, NotFoundException {
        BookModel bookModel = bookMapper.entityToModel(bookEntity);
        bookRepository.update(bookModel);
    }

    public void deleteBook(int id) throws NotFoundException, ConstraintViolationException {
        bookRepository.delete(id);
    }
    
}
