package com.esgi.presentation.cli.books;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.domain.books.BookServiceFactory;
import com.esgi.domain.genres.GenreServiceFactory;
import com.esgi.presentation.cli.books.add.AddBookCliCommandNode;
import com.esgi.presentation.cli.books.delete.DeleteBookCliCommandNode;
import com.esgi.presentation.cli.books.edit.EditBookCliCommandNode;
import com.esgi.presentation.cli.books.export.ExportBooksCliCommandNode;
import com.esgi.presentation.cli.books.importation.ImportBooksCliCommandNode;
import com.esgi.presentation.cli.books.list.ListBookCliCommandNode;
import com.esgi.presentation.cli.books.search.SearchBookCliCommandNode;
import com.esgi.presentation.cli.books.unborrowed.UnborrowedBookCliCommandeNode;

public final class BookCliCommandFactory {

    public static BookCliCommandNode makeBookCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var bookService = BookServiceFactory.getBookService();
        var authorService = AuthorServiceFactory.getAuthorService();
        var genreService = GenreServiceFactory.getGenreService();
        var loanService = LoanServiceFactory.getLoanService();

        var addBookCommand = new AddBookCliCommandNode(bookService, authorService, genreService);
        var editBookCommand = new EditBookCliCommandNode(bookService, authorService, genreService);
        var deleteBookCommand = new DeleteBookCliCommandNode(bookService);
        var listBookCommand = new ListBookCliCommandNode(bookService);
        var searchBookCommand = new SearchBookCliCommandNode(bookService);
        var unborrowedBookCommand = new UnborrowedBookCliCommandeNode(bookService,loanService);
        var exportBooksCommand = new ExportBooksCliCommandNode(bookService);
        var importBooksCommand = new ImportBooksCliCommandNode(bookService,authorService,genreService);

        return new BookCliCommandNode(
            authService,
            addBookCommand,
            editBookCommand,
            deleteBookCommand,
            listBookCommand,
            searchBookCommand,
            exportBooksCommand,
            importBooksCommand,
                unborrowedBookCommand
        );
    }
}
