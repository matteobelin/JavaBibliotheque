package com.esgi.presentation.menus.items.authors;

import com.esgi.presentation.menus.items.MenuItem;

public class AddAuthorItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add an author";
    }

    @Override
    public void execute() throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return true;
    }
}
