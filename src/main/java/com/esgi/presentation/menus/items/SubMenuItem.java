package com.esgi.presentation.menus.items;

import com.esgi.presentation.menus.Menu;

import java.util.function.Supplier;

public class SubMenuItem implements MenuItem {
    final String title;
    final Menu subMenu;
    final boolean needsAdmin;

    public SubMenuItem(String title, Menu subMenu) {
        this.title = title;
        this.subMenu = subMenu;
        this.needsAdmin = false;
    }

    public SubMenuItem(String title, Menu subMenu, boolean needsAdmin) {
        this.title = title;
        this.subMenu = subMenu;
        this.needsAdmin = needsAdmin;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void execute() {
        subMenu.display();
    }

    @Override
    public boolean needsAdmin() {
        return needsAdmin;
    }
}
