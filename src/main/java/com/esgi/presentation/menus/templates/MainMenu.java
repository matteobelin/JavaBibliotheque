package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.account.LoginItem;
import com.esgi.presentation.menus.items.books.SearchBookItem;
import com.esgi.presentation.menus.items.account.SignInItem;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Welcome to biblio !");

        addItem(new SearchBookItem());
        addItem(new LoginItem());
        addItem(new SignInItem());
    }
}