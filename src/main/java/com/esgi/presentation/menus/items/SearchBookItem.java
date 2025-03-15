package com.esgi.presentation.menus.items;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.menus.MenuItem;

public class SearchBookItem extends MenuItem {
    public SearchBookItem() {
        super("search a book");
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}