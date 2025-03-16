package com.esgi.presentation.menus.items.Account;

import com.esgi.domain.AppContext;
import com.esgi.domain.auth.AuthCredentials;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.users.UserEntity;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;
import com.esgi.presentation.menus.templates.HomeMenu;

import java.util.Scanner;

public class LoginItem implements MenuItem {

    @Override
    public String getTitle() {
        return "login";
    }

    @Override
    public void execute() throws Exception {
        UserEntity loggedUser = null;
        Scanner scanner = new Scanner(System.in);

        AppLogger.info("Enter your email");
        String email = scanner.nextLine();

        AppLogger.info("Enter your password");
        String password = scanner.nextLine();

        AppLogger.info("Stay logged in ? [y/n]");
        String answer = scanner.nextLine();
        boolean stayLoggedIn = answer.equals("y");

        AuthService authService = AuthServiceFactory.getAuthService();
        AuthCredentials credentials = new AuthCredentials(email, password, stayLoggedIn);

        loggedUser = authService.login(credentials);

        AppContext.getInstance().setLoggedInUser(loggedUser);
        Menu homeMenu = new HomeMenu();
        homeMenu.display();
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}
