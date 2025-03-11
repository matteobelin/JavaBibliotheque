package com.esgi.presentation.cli.genres.list;

import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class ListGenreCliCommandNode extends CliCommandNode {

    public static final String NAME = "list";
    public static final String DESCRIPTION = "List all genres in the library.";

    private final GenreService genreService;

    public ListGenreCliCommandNode(GenreService genreService) {
        super(NAME, DESCRIPTION);
        this.genreService = genreService;
    }

    @Override
    public ExitCode run(String[] args) {

        var genres = this.genreService.getAllGenres();

        for (int i = 0; i < genres.size(); i++) {
            AppLogger.info("%d. %s".formatted(i + 1, genres.get(i).getName()));
        }

        return ExitCode.OK;
    }
}
