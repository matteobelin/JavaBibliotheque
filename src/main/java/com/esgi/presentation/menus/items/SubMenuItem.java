package com.esgi.presentation.menus.items;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.menus.MenuItem;
import com.esgi.presentation.menus.Menus;

import java.util.Optional;
import java.util.function.Supplier;

public class SubMenuItem extends MenuItem {
    private final Supplier<Menus> menusSupplier;

    public SubMenuItem(String title, Supplier<Menus> menusSupplier) {
        super(title);
        this.menusSupplier = menusSupplier;
    }

    @Override
    public void execute() {
        Menus subMenu = menusSupplier.get();
        subMenu.display();
    }
}
