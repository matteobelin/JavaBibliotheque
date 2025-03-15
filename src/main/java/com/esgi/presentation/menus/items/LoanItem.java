package com.esgi.presentation.menus.items;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.menus.MenuItem;

public class LoanItem extends MenuItem {
    public LoanItem() {
        super("loan a book");
    }

    @Override
    public void execute() {
        throw new RuntimeException("Not implemented");
    }
}
