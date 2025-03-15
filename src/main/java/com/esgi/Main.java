package com.esgi;

import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.cli.CliEntryPoint;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import com.esgi.presentation.cli.users.UserCliCommandNode;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;
import com.esgi.presentation.menus.templates.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();

        initAppFolderConfig();

        AuthService authService = AuthServiceFactory.getAuthService();

        try {
            authService.tryToLoginWithSavedCredentials();
        } catch (IncorrectCredentialsException e) {
            AppLogger.warn("There was an error logging you in. Please login manually.");
        }

        if(args != null && args.length > 0) {
            AddUserCliCommandNode addUserCliCommandNode = new AddUserCliCommandNode(userService);
            UserCliCommandNode userCliCommandNode = new UserCliCommandNode(addUserCliCommandNode);

            var cliEntryPoint = new CliEntryPoint(userCliCommandNode);
            ExitCode exitCode = cliEntryPoint.run(args);
            System.exit(exitCode.ordinal());
            return;
        }

        AppLogger.writeLines(AppLoggerColorEnum.GREEN, BIBLIO_ART_LIST);
        AppLogger.emptyLine();
        // TODO: MENU
        mainMenu.display();
    }
}