package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.LoanItem;
import com.esgi.presentation.menus.items.SearchBookItem;

public class HomeMenu extends Menu {
    public HomeMenu(Menu parent) {
        super("Welcome back [USER] !", "Home", parent);

        addItem(new SearchBookItem());
        addItem(new LoanItem());
    }
}