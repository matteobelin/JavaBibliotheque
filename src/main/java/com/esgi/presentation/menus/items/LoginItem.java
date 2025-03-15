package com.esgi.presentation.menus.items;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.menus.MenuItem;

public class LoginItem extends MenuItem {
    public LoginItem() {
        super("login");
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}
