package com.esgi.presentation.menus.items.loans;

import com.esgi.presentation.menus.items.MenuItem;

public class AddLoanItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add a loan";
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
