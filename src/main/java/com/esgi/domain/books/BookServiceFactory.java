package com.esgi.domain.books;

import com.esgi.data.books.BookRepository;
import com.esgi.data.books.BookRepositoryFactory;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.domain.books.impl.BookServiceImpl;
import com.esgi.domain.genres.GenreService;
import com.esgi.domain.genres.GenreServiceFactory;


public class BookServiceFactory {
    private static BookService bookService;

    public static BookService getBookService() {
        if (bookService == null) {
            bookService = BookServiceFactory.makeBookService();
        }
        return bookService;
    }

    public static BookService makeBookService() {
        AuthorService authorService = AuthorServiceFactory.getAuthorService();
        GenreService genreService = GenreServiceFactory.getGenreService();

        BookMapper bookMapper = new BookMapper(authorService, genreService);

        BookRepository bookRepository = BookRepositoryFactory.makeBookRepository();
        return new BookServiceImpl(bookRepository, bookMapper);
    }
}
