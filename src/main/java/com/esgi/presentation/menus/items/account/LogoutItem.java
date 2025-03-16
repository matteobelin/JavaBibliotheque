package com.esgi.presentation.menus.items.account;

import com.esgi.domain.AppContext;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;
import com.esgi.presentation.menus.templates.MainMenu;

public class LogoutItem implements MenuItem {
    @Override
    public String getTitle() {
        return "logout";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        AppContext.getInstance().setLoggedInUser(null);

        AuthService authService = AuthServiceFactory.getAuthService();
        authService.logout();

        Menu mainMenu = new MainMenu();
        mainMenu.display();
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
