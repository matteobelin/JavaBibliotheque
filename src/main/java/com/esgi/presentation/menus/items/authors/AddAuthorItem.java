package com.esgi.presentation.menus.items.authors;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class AddAuthorItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add an author";
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
