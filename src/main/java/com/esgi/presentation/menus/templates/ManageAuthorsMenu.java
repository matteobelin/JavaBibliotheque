package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.authors.AddAuthorItem;
import com.esgi.presentation.menus.items.authors.EditAuthorItem;
import com.esgi.presentation.menus.items.authors.RemoveAuthorItem;

public class ManageAuthorsMenu extends Menu {
    public ManageAuthorsMenu(Menu previousMenu) {
        super("Manage Authors", "Authors", previousMenu);

        addItem(new AddAuthorItem());
        addItem(new EditAuthorItem());
        addItem(new RemoveAuthorItem());
    }
}
