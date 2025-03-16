package com.esgi.presentation.menus.items.Genres;

import com.esgi.presentation.menus.items.MenuItem;

public class EditGenreItem implements MenuItem {
    @Override
    public String getTitle() {
        return "edit a genre";
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
