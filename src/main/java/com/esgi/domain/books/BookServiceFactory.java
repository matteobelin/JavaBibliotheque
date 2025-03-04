package com.esgi.domain.books;

import com.esgi.data.authors.AuthorRepository;
import com.esgi.data.authors.AuthorRepositoryFactory;
import com.esgi.data.books.BookRepository;
import com.esgi.data.books.BookRepositoryFactory;
import com.esgi.data.genres.GenreRepository;
import com.esgi.data.genres.GenreRepositoryFactory;
import com.esgi.domain.authors.AuthorMapper;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.authors.impl.AuthorServiceImpl;
import com.esgi.domain.books.impl.BookServiceImpl;
import com.esgi.domain.genres.GenreMapper;
import com.esgi.domain.genres.GenreService;
import com.esgi.domain.genres.impl.GenreServiceImpl;

public class BookServiceFactory {
    public static BookService makeBookService() {
        AuthorRepository authorRepository = AuthorRepositoryFactory.makeAuthorsRepository();
        AuthorMapper authorMapper = new AuthorMapper();
        AuthorService authorService = new AuthorServiceImpl(authorRepository, authorMapper);

        GenreRepository genreRepository = GenreRepositoryFactory.makeGenreRepository();
        GenreMapper genreMapper = new GenreMapper();
        GenreService genreService = new GenreServiceImpl(genreRepository, genreMapper);

        BookMapper bookMapper = new BookMapper(authorService, genreService);

        BookRepository bookRepository = BookRepositoryFactory.makeBookRepository();
        return new BookServiceImpl(bookRepository, bookMapper);
    }
}
