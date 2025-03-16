package com.esgi.presentation.menus.items.loans;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class SeeHistoryItem implements MenuItem {
    @Override
    public String getTitle() {
        return "see loan history";
    }

    @Override
    public void execute(Menu menu) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
