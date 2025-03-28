package com.esgi.presentation.cli;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.cli.auth.AuthCliCommandNodeFactory;
import com.esgi.presentation.cli.authors.AuthorCliCommandFactory;
import com.esgi.presentation.cli.books.BookCliCommandFactory;
import com.esgi.presentation.cli.genres.GenreCliCommandFactory;
import com.esgi.presentation.cli.loans.LoanCliCommandFactory;
import com.esgi.presentation.cli.users.UserCliCommandFactory;

public final class CliEntryPointFactory {

    public static CliEntryPoint makeCliEntryPoint() {
        var authService = AuthServiceFactory.getAuthService();

        var userCliCommandNode = UserCliCommandFactory.makeUserCliCommandNode();
        var authCliCommandNode = AuthCliCommandNodeFactory.makeAuthCliCommandNode();
        var authorCliCommandNode = AuthorCliCommandFactory.makeAuthorCliCommandNode();
        var genreCliCommandNode = GenreCliCommandFactory.makeGenreCliCommandNode();
        var bookCliCommandNode = BookCliCommandFactory.makeBookCliCommandNode();
        var loanCliCommandNode = LoanCliCommandFactory.makeLoanCliCommandNode();

        return new CliEntryPoint(
            authService,
            authCliCommandNode,
            userCliCommandNode,
            authorCliCommandNode,
            genreCliCommandNode,
            bookCliCommandNode,
            loanCliCommandNode
        );
    }
}
