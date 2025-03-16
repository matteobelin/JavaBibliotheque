package com.esgi.presentation.menus.items.account;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

public class ChangeEmailItem implements MenuItem {
    @Override
    public String getTitle() {
        return "change email";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
