package com.esgi.presentation.menus.items.loans;

import com.esgi.presentation.menus.items.MenuItem;

public class SeeHistoryItem implements MenuItem {
    @Override
    public String getTitle() {
        return "see loan history";
    }

    @Override
    public void execute() throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
