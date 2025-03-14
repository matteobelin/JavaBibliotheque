package com.esgi.presentation.cli.books;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.books.add.AddBookCliCommandNode;
import com.esgi.presentation.cli.books.delete.DeleteBookCliCommandNode;
import com.esgi.presentation.cli.books.edit.EditBookCliCommandNode;
import com.esgi.presentation.cli.books.export.ExportBooksCliCommandNode;
import com.esgi.presentation.cli.books.list.ListBookCliCommandNode;

import java.util.List;

public class BookCliCommandNode extends CliCommandNode {

    public static final String NAME = "books";

    public static final String DESCRIPTION = "Managers books in the library";

    public BookCliCommandNode(
          AuthService authService,
          AddBookCliCommandNode addBookCommand,
          EditBookCliCommandNode editBookCommand,
          DeleteBookCliCommandNode deleteBookCommand,
          ListBookCliCommandNode listBookCommand,
          ExportBooksCliCommandNode exportBooksCommand
    ) {
        super(NAME, DESCRIPTION);

        if (authService.isLoggedInUserAdmin()) {
            this.childrenCommands.add(addBookCommand);
            this.childrenCommands.add(editBookCommand);
            this.childrenCommands.add(deleteBookCommand);
        }

        this.childrenCommands.add(listBookCommand);
        this.childrenCommands.add(exportBooksCommand);

        this.childrenCommands.add(new HelpCliCommand(List.copyOf(this.getChildrenCommands())));
    }
}
