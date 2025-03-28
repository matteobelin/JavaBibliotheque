package com.esgi.presentation.cli.authors;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.presentation.cli.authors.add.AddAuthorCliCommandNode;
import com.esgi.presentation.cli.authors.delete.DeleteAuthorCliCommandNode;
import com.esgi.presentation.cli.authors.list.ListAuthorsCliCommandNode;

public final class AuthorCliCommandFactory {

    public static AuthorCliCommandNode makeAuthorCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var authorService = AuthorServiceFactory.getAuthorService();

        var addAuthorCommand = new AddAuthorCliCommandNode(authorService);
        var deleteAuthorCommand = new DeleteAuthorCliCommandNode(authorService);
        var listAuthorCommand = new ListAuthorsCliCommandNode(authorService);


        return new AuthorCliCommandNode(authService, addAuthorCommand, deleteAuthorCommand, listAuthorCommand);
    }

}
