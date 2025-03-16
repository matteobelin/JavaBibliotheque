package com.esgi.presentation.menus.items.books;

import com.esgi.presentation.menus.items.MenuItem;

public class AddBookItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add a book";
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return true;
    }
}
