package com.esgi.presentation.menus.items.Genres;

import com.esgi.presentation.menus.items.MenuItem;

public class RemoveGenreItem implements MenuItem {
    @Override
    public String getTitle() {
        return "remove a genre";
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
