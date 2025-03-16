package com.esgi.presentation.menus.templates;

import com.esgi.domain.AppContext;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.LoanItem;
import com.esgi.presentation.menus.items.SearchBookItem;

public class HomeMenu extends Menu {
    public HomeMenu() {
        super("Welcome back "
                + AppContext.getInstance().getUserName()
                + " !",
            "Home");

        addItem(new SearchBookItem());
        addItem(new LoanItem());
    }

    public HomeMenu(Menu parent) {
        super("Welcome back"
                    + AppContext.getInstance().getUserName()
                    + " !", "Home",
                parent);

        addItem(new SearchBookItem());
        addItem(new LoanItem());
    }
}