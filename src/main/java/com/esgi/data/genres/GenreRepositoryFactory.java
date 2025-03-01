package com.esgi.data.genres;

import com.esgi.data.genres.impl.GenreRepositoryImpl;

public class GenreRepositoryFactory {
    public static GenreRepository makeGenreRepository(){return new GenreRepositoryImpl();}
}
