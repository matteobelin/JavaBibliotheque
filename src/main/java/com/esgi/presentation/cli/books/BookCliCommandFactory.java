package com.esgi.presentation.cli.books;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.authors.AuthorServiceFactory;
import com.esgi.domain.books.BookServiceFactory;
import com.esgi.domain.genres.GenreServiceFactory;
import com.esgi.presentation.cli.books.add.AddBookCliCommandNode;
import com.esgi.presentation.cli.books.delete.DeleteBookCliCommandNode;
import com.esgi.presentation.cli.books.edit.EditBookCliCommandNode;
import com.esgi.presentation.cli.books.list.ListBookCliCommandNode;

public final class BookCliCommandFactory {

    public static BookCliCommandNode makeBookCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var bookService = BookServiceFactory.getBookService();
        var authorService = AuthorServiceFactory.getAuthorService();
        var genreService = GenreServiceFactory.getGenreService();

        var addBookCommand = new AddBookCliCommandNode(bookService, authorService, genreService);
        var editBookCommand = new EditBookCliCommandNode(bookService, authorService, genreService);
        var deleteBookCommand = new DeleteBookCliCommandNode(bookService);
        var listBookCommand = new ListBookCliCommandNode(bookService);

        return new BookCliCommandNode(authService, addBookCommand, editBookCommand, deleteBookCommand, listBookCommand);
    }
}
