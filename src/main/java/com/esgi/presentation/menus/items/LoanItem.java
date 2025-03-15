package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

public class LoanItem implements MenuItem {
    @Override
    public String getTitle() {
        return "loan a book";
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}
