package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.MenuItem;
import com.esgi.presentation.menus.Menus;
import com.esgi.presentation.menus.items.LoanItem;
import com.esgi.presentation.menus.items.SearchBookItem;

import java.util.ArrayList;
import java.util.List;

public class HomeMenu extends Menus {
    public HomeMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new SearchBookItem());
        menuItems.add(new LoanItem());
        super("Welcome back !", menuItems, "Home\n", MainMenu::new);
    }
}
