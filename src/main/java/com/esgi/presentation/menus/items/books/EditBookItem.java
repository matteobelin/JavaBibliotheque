package com.esgi.presentation.menus.items.books;

import com.esgi.presentation.menus.items.MenuItem;

public class EditBookItem implements MenuItem {
    @Override
    public String getTitle() {
        return "edit a book";
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
