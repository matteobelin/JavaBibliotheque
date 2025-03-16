package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

public class SignInItem implements MenuItem {

    @Override
    public String getTitle() {
        return "sign in";
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}