package com.esgi.presentation.cli.genres.list;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListGenreCliCommandNode extends CliCommandNode {

    public static final String NAME = "list";
    public static final String DESCRIPTION = "List all genres in the library.";

    private final GenreService genreService;

    public ListGenreCliCommandNode(GenreService genreService) {
        super(NAME, DESCRIPTION);
        this.genreService = genreService;
    }

    @Override
    public ExitCode run(String[] args) throws InternalErrorException {
        var tableHeader = List.of(
                "#",
                "Name"
        );
        var tableRows = new ArrayList<List<String>>();
        tableRows.add(tableHeader);

        List<GenreEntity> genres = this.genreService.getAllGenres();

        for (int i = 0; i < genres.size(); i++) {
            var genre = genres.get(i);
            var tableRow = this.mapGenreToTableRow(i + 1, genre);
            tableRows.add(tableRow);
        }

        var table = StringUtils.makeTable(tableRows);
        AppLogger.emptyLine();
        AppLogger.info(table);

        return ExitCode.OK;
    }

    private List<String> mapGenreToTableRow(Integer i, GenreEntity genre) {
        return List.of(
            i.toString(),
            genre.getName()
        );
    }
}
