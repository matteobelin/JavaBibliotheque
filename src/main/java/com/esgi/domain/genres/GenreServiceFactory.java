package com.esgi.domain.genres;

import com.esgi.data.genres.GenreRepository;
import com.esgi.data.genres.GenreRepositoryFactory;
import com.esgi.domain.genres.impl.GenreServiceImpl;

public class GenreServiceFactory {
    private static GenreService genreService;

    public static GenreService getGenreService() {
        if (genreService == null) {
            genreService = makeGenreService();
        }
        return genreService;
    }

    public static GenreService makeGenreService() {
        GenreRepository genreRepository = GenreRepositoryFactory.makeGenreRepository();
        GenreMapper genreMapper = new GenreMapper();
        return new GenreServiceImpl(genreRepository,genreMapper);
    }
}
