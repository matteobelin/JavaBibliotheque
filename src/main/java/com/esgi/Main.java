package com.esgi;

import com.esgi.core.exceptions.IncorrectCredentialsException;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.AppContext;

import com.esgi.domain.auth.AuthService;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.AppFolderConfig;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.AppLoggerColorEnum;
import com.esgi.presentation.cli.CliEntryPointFactory;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.templates.HomeMenu;
import com.esgi.presentation.menus.templates.MainMenu;
import com.esgi.presentation.utils.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        initAppFolderConfig();
        AppContext context = AppContext.getInstance();
        AuthService authService = AuthServiceFactory.getAuthService();

        try {
            authService.tryToLoginWithSavedCredentials();
            context.setLoggedInUser(authService.getLoggedInUser());
        } catch (IncorrectCredentialsException e) {
            AppLogger.warn("There was an error logging you in. Please login manually.");
        }

        if(args != null && args.length > 0) {
            runCLICommand(args);
            return;
        }

        Menu menu;
        AppLogger.writeLines(AppLoggerColorEnum.GREEN, BIBLIO_ART_LIST);
        AppLogger.emptyLine();

        if (context.isAdmin()) {
            menu = new HomeMenu();
        } else {
            menu = new MainMenu();
        }

        menu.display();
    }

    private static void initAppFolderConfig() {
        try {
            AppFolderConfig appFolderConfig = new AppFolderConfig();
            appFolderConfig.initAppFolderConfig();
        } catch (IOException | URISyntaxException e) {
            var Lines = List.of(
                    "There was an error creating the folder used by the app to store data.",
                    "Error message : " + e.getMessage()
            );
            AppLogger.error(StringUtils.wrapInLargeBox(Lines));

            System.exit(ExitCode.INTERNAL_ERROR.ordinal());
        }
    }

    private static void runCLICommand(String[] args) {
        var authService = AuthServiceFactory.getAuthService();
        boolean isNotLoggedIn = !authService.isLoggedIn();

        if (isNotLoggedIn) {
            warnNotLoggedInUserCliCommandsLimited();
        }

        var cliEntryPoint = CliEntryPointFactory.makeCliEntryPoint();

        ExitCode exitCode;
        try {
            exitCode = cliEntryPoint.run(args);
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            exitCode = ExitCode.INTERNAL_ERROR;
        }

        System.exit(exitCode.ordinal());
    }

    private static void warnNotLoggedInUserCliCommandsLimited() {
        List<String> lines = StringUtils.wrapInLargeBox(List.of(
                "You are not logged in. Some commands are not available.",
                "-> You can use the help command (biblio help) to see the list of commands available to you."
        ));

        AppLogger.writeLines(AppLoggerColorEnum.ORANGE, lines);
        AppLogger.emptyLine();
    }

    private static final List<String> BIBLIO_ART_LIST = List.of(
            "    ___       ___       ___       ___      ___       ___   ",
            "   /\\  \\     /\\  \\     /\\  \\     /\\__\\    /\\  \\     /\\  \\  ",
            "  /::\\  \\   _\\:\\  \\   /::\\  \\   /:/  /   _\\:\\  \\   /::\\  \\ ",
            " /::\\:\\__\\ /\\/::\\__\\ /::\\:\\__\\ /:/__/   /\\/::\\__\\ /:/\\:\\__\\",
            " \\:\\::/  / \\::/\\/__/ \\:\\::/  / \\:\\  \\   \\::/\\/__/ \\:\\/:/  /",
            "  \\::/  /   \\:\\__\\    \\::/  /   \\:\\__\\   \\:\\__\\    \\::/  / ",
            "   \\/__/     \\/__/     \\/__/     \\/__/    \\/__/     \\/__/  "
    );

}