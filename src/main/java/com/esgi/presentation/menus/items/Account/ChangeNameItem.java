package com.esgi.presentation.menus.items.Account;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class ChangeNameItem implements MenuItem {
    @Override
    public String getTitle() {
        return "chane username";
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
