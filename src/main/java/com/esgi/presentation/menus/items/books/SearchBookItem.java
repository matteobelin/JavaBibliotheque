package com.esgi.presentation.menus.items.books;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class SearchBookItem implements MenuItem {
    @Override
    public String getTitle() {
        return "search a book";
    }

    @Override
    public void execute(Menu menu) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return true;
    }
}