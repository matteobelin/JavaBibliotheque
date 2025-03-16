package com.esgi.presentation.menus.items.Account;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.domain.AppContext;
import com.esgi.domain.auth.AuthCredentials;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;
import com.esgi.presentation.menus.templates.HomeMenu;

import java.util.Scanner;

public class SignInItem implements MenuItem {

    @Override
    public String getTitle() {
        return "sign in";
    }

    @Override
    public void execute() throws Exception {
        Scanner scanner = new Scanner(System.in);
        UserEntity newUser = new UserEntity();

        AppLogger.info("Enter your email address: ");
        newUser.setEmail(scanner.nextLine());
        AppLogger.info("Enter your name: ");
        newUser.setName(scanner.nextLine());
        AppLogger.info("Enter your password: ");
        newUser.setPassword(scanner.nextLine());
        newUser.setAdmin(false);

        UserService userService = UserServiceFactory.getUserService();
        try {
            userService.createUser(newUser);
        } catch (ConstraintViolationException e) {
            AppLogger.error("An user with this email address already exists!");
            execute();
        }

        AppContext.getInstance().setLoggedInUser(newUser);
        Menu homeMenu = new HomeMenu();
        homeMenu.display();
    }

    @Override
    public boolean needsAdmin() {
        return false;
    }
}