package com.esgi.presentation.menus.items.users;

import com.esgi.presentation.menus.items.MenuItem;

public class AddUserItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add a user";
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