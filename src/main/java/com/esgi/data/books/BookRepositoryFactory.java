package com.esgi.data.books;

import com.esgi.data.books.impl.BookRepositoryImpl;
import com.esgi.data.genreBook.GenreBookRepositoryFactory;

public class BookRepositoryFactory {
    public static BookRepository makeBookRepository() {
        var genreBookRepository = GenreBookRepositoryFactory.makeGenreBookRepository();

        return new BookRepositoryImpl(genreBookRepository);
    }
}
