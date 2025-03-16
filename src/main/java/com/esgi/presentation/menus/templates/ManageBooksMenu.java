package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.books.AddBookItem;
import com.esgi.presentation.menus.items.books.EditBookItem;
import com.esgi.presentation.menus.items.books.RemoveBookItem;

public class ManageBooksMenu extends Menu {
    public ManageBooksMenu(Menu previousMenu) {
        super("Manage Books", "Books", previousMenu);

        addItem(new AddBookItem());
        addItem(new EditBookItem());
        addItem(new RemoveBookItem());
    }
}
