package com.esgi.presentation.cli.authors;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.presentation.cli.authors.add.AddAuthorCliCommandNode;
import com.esgi.presentation.cli.authors.delete.DeleteAuthorCliCommandNode;

public final class AuthorCliCommandFactory {

    public static AuthorCliCommandNode makeAuthorCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var authorService = AuthorServiceFactory.makeAuthorService();

        var addAuthorCommand = new AddAuthorCliCommandNode(authorService);
        var deleteAuthorCommand = new DeleteAuthorCliCommandNode(authorService);


        return new AuthorCliCommandNode(authService, addAuthorCommand, deleteAuthorCommand);
    }

}
