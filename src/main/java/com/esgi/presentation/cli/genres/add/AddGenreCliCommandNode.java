package com.esgi.presentation.cli.genres.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class AddGenreCliCommandNode extends CliCommandNode {

    public static final String NAME = "add";

    public static final String DESCRIPTION = "Add genres to the library.";

    private final GenreService genreService;

    public AddGenreCliCommandNode(GenreService genreService) {
        super(NAME, DESCRIPTION);
        this.genreService = genreService;
    }

    @Override
    public ExitCode run(String[] args) {

        var values = this.extractValuesFromArgs(args);

        if (values.isEmpty()) {
            AppLogger.error("This command required 1 argument : biblio genres add NAME");
            return ExitCode.ARGUMENT_MISSING;
        }

        String genreName = values.get(0);

        var genre = new GenreEntity();
        genre.setName(genreName);

        try {
            this.genreService.createGenre(genre);

            AppLogger.success("The genre '%s' has been created !".formatted(genreName));
        } catch (ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }
}
