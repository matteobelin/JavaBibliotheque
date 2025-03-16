package com.esgi.presentation.cli.genres;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.genres.add.AddGenreCliCommandNode;
import com.esgi.presentation.cli.genres.delete.DeleteGenreCliCommandNode;
import com.esgi.presentation.cli.genres.list.ListGenreCliCommandNode;

import java.util.List;

public class GenreCliCommandNode extends CliCommandNode {

    public static final String NAME = "genres";
    public static final String DESCRIPTION = "Managers genres in the library.";


    public GenreCliCommandNode(
           AuthService authService,
           AddGenreCliCommandNode addGenreCommand,
           DeleteGenreCliCommandNode deleteGenreCommand,
           ListGenreCliCommandNode listGenreCommand
    ) {
        super(NAME, DESCRIPTION);

        if (authService.isLoggedInUserAdmin()) {
            this.childrenCommands.add(addGenreCommand);
            this.childrenCommands.add(deleteGenreCommand);
        }

        this.childrenCommands.add(listGenreCommand);

        this.childrenCommands.add(new HelpCliCommand(List.copyOf(this.getChildrenCommands())));
    }
}
