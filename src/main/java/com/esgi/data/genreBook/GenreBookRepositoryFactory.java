package com.esgi.data.genreBook;

import com.esgi.data.genreBook.impl.GenreBookRepositoryImpl;

public final class GenreBookRepositoryFactory {

    public static GenreBookRepository makeGenreBookRepository() {
        return new GenreBookRepositoryImpl();
    }
}
