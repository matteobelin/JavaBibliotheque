package com.esgi.presentation.menus.items.loans;

import com.esgi.presentation.menus.items.MenuItem;

public class SeeHistoryAdminItem implements MenuItem {
    @Override
    public String getTitle() {
        return "see loan history";
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
