package com.esgi.presentation.cli.genres;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.genres.GenreServiceFactory;
import com.esgi.presentation.cli.genres.add.AddGenreCliCommandNode;
import com.esgi.presentation.cli.genres.delete.DeleteGenreCliCommandNode;
import com.esgi.presentation.cli.genres.list.ListGenreCliCommandNode;

public final class GenreCliCommandFactory {

    public static GenreCliCommandNode makeGenreCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var genreService = GenreServiceFactory.makeGenreService();

        var addGenreCommand = new AddGenreCliCommandNode(genreService);
        var deleteGenreCommand = new DeleteGenreCliCommandNode(genreService);
        var listGenreCommand = new ListGenreCliCommandNode(genreService);

        return new GenreCliCommandNode(authService, addGenreCommand, deleteGenreCommand, listGenreCommand);
    }
}
