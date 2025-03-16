package com.esgi.presentation.cli.genres.delete;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class DeleteGenreCliCommandNode extends CliCommandNode {

    public static final String NAME = "delete";
    public static final String DESCRIPTION = "Delete a genre";

    private final GenreService genreService;

    public DeleteGenreCliCommandNode(GenreService genreService) {
        super(NAME, DESCRIPTION);
        this.genreService = genreService;
    }

    @Override
    public ExitCode run(String[] args) throws InternalErrorException {
        var values = this.extractValuesFromArgs(args);

        if (values.isEmpty()) {
            AppLogger.error("This commands requires the name of the genre to delete : biblio genres delete NAME");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            this.genreService.deleteGenre(values.get(0));

            AppLogger.success("Genre with name %s deleted !".formatted(values.get(0)));
        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }

}
