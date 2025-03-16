package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.LoginItem;
import com.esgi.presentation.menus.items.SearchBookItem;
import com.esgi.presentation.menus.items.SignInItem;
import com.esgi.presentation.menus.items.SubMenuItem;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Welcome to biblio !");

        Menu homeMenu = new HomeMenu(this);
        addItem(new SearchBookItem());
        addItem(new LoginItem());
        addItem(new SignInItem());
        addItem(new SubMenuItem("home", homeMenu, true));
    }
}