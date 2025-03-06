package com.esgi.domain.books;

import com.esgi.data.books.BookRepository;
import com.esgi.data.books.BookRepositoryFactory;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.domain.books.impl.BookServiceImpl;
import com.esgi.domain.genres.GenreService;
import com.esgi.domain.genres.GenreServiceFactory;


public class BookServiceFactory {
    public static BookService makeBookService() {
        AuthorService authorService = AuthorServiceFactory.makeAuthorService();
        GenreService genreService = GenreServiceFactory.makeGenreService();

        BookMapper bookMapper = new BookMapper(authorService, genreService);

        BookRepository bookRepository = BookRepositoryFactory.makeBookRepository();
        return new BookServiceImpl(bookRepository, bookMapper);
    }
}
