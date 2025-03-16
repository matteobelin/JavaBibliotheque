package com.esgi.presentation.menus.items.authors;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class EditAuthorItem implements MenuItem {
    @Override
    public String getTitle() {
        return "edit an author";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return true;
    }
}
