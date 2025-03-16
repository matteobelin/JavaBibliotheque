package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.SubMenuItem;

public class AdminMenu extends Menu {
    public AdminMenu(Menu previousMenu) {
        super("Admin Panel", "Admin", previousMenu);

        addItem(new SubMenuItem(
                "manage books",
                new ManageBooksMenu(this),
                true
        ));
        addItem(new SubMenuItem(
                "manage users",
                new ManageUsersMenu(this),
                true
        ));
        addItem(new SubMenuItem(
                "manage loans",
                new ManageLoansMenu(this),
                true
        ));
        addItem(new SubMenuItem(
                "manage authors",
                new ManageAuthorsMenu(this),
                true
        ));
        addItem(new SubMenuItem(
                "manage genres",
                new ManageGenresMenu(this),
                true
        ));
    }
}
