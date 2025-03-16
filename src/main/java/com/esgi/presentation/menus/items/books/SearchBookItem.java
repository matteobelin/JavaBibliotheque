package com.esgi.presentation.menus.items.books;

import com.esgi.domain.books.BookService;
import com.esgi.domain.books.BookServiceFactory;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

import java.util.Scanner;

public class SearchBookItem implements MenuItem {
    @Override
    public String getTitle() {
        return "search a book";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        Scanner scanner = new Scanner(System.in);

        AppLogger.info("Enter search term: ");
        String searchTerm = scanner.nextLine();

        BookService bookService = BookServiceFactory.getBookService();
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}