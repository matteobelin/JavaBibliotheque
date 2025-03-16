package com.esgi.presentation.menus.items.users;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class RemoveUserItem implements MenuItem {
    @Override
    public String getTitle() {
        return "remove a user";
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
