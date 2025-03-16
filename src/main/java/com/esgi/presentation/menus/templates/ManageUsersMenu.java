package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.users.AddUserItem;
import com.esgi.presentation.menus.items.users.EditUserItem;
import com.esgi.presentation.menus.items.users.RemoveUserItem;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu previousMenu) {
        super("Manage Users", "Users", previousMenu);

        addItem(new AddUserItem());
        addItem(new EditUserItem());
        addItem(new RemoveUserItem());
    }
}
