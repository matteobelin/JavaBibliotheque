package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

public class SearchBookItem implements MenuItem {
    @Override
    public String getTitle() {
        return "search a book";
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