package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

public class LoginItem implements MenuItem {

    @Override
    public String getTitle() {
        return "login";
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}
