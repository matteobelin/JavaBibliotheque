package com.esgi.presentation.menus.items.Genres;

import com.esgi.presentation.menus.items.MenuItem;

public class AddGenreItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add a genre";
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
