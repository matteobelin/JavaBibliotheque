package com.esgi.presentation.menus.templates;

import com.esgi.domain.AppContext;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.*;
import com.esgi.presentation.menus.items.books.SearchBookItem;
import com.esgi.presentation.menus.items.loans.LoanBookItem;
import com.esgi.presentation.menus.items.loans.ReturnBookItem;
import com.esgi.presentation.menus.items.loans.SeeHistoryItem;
import com.esgi.presentation.menus.items.loans.SeeLoansItem;

public class HomeMenu extends Menu {
    public HomeMenu() {
        super("Welcome back "
                + AppContext.getInstance().getUserName()
                + " !",
            "Home");
        Menu adminMenu = new AdminMenu(this);
        Menu manageAccount = new ManageAccountMenu(this);
        addItem(new SubMenuItem("admin", adminMenu, true));
        addItem(new SearchBookItem());
        addItem(new LoanBookItem());
        addItem(new ReturnBookItem());
        addItem(new SeeLoansItem());
        addItem(new SeeHistoryItem());
        addItem(new SubMenuItem("manage account", manageAccount));
    }
}