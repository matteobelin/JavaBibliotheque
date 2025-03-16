package com.esgi.presentation.menus.items.Account;

import com.esgi.presentation.menus.items.MenuItem;

public class SignInItem implements MenuItem {

    @Override
    public String getTitle() {
        return "sign in";
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