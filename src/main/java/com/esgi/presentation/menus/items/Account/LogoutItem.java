package com.esgi.presentation.menus.items.Account;

import com.esgi.presentation.menus.items.MenuItem;

public class LogoutItem implements MenuItem {
    @Override
    public String getTitle() {
        return "logout";
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
