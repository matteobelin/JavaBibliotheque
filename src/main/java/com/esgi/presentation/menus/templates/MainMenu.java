package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.MenuItem;
import com.esgi.presentation.menus.Menus;
import com.esgi.presentation.menus.items.LoginItem;
import com.esgi.presentation.menus.items.SearchBookItem;
import com.esgi.presentation.menus.items.SignInItem;
import com.esgi.presentation.menus.items.SubMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends Menus {
    public MainMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new SearchBookItem());
        menuItems.add(new LoginItem());
        menuItems.add(new SignInItem());
        menuItems.add(new SubMenuItem("Home", HomeMenu::new));

        super("Welcome to Biblio !", menuItems, "");
    }
}
