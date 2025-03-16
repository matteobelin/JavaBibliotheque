package com.esgi.presentation.menus.templates;

import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.account.ChangeEmailItem;
import com.esgi.presentation.menus.items.account.ChangeNameItem;
import com.esgi.presentation.menus.items.account.LogoutItem;

public class ManageAccountMenu extends Menu {
    public ManageAccountMenu(Menu previousMenu) {
        super("Manage Account", "Account", previousMenu);

        addItem(new ChangeEmailItem());
        addItem(new ChangeNameItem());
        addItem(new LogoutItem());
    }
}
