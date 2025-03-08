package com.esgi;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.AppLoggerColorEnum;
import com.esgi.presentation.cli.CliEntryPoint;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.auth.AuthCliCommandNode;
import com.esgi.presentation.cli.users.UserCliCommandFactory;
import com.esgi.presentation.utils.StringUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthService authService = AuthServiceFactory.getAuthService();

        try {
            authService.tryToLoginWithSavedCredentials();
        } catch (IncorrectCredentialsException e) {
            AppLogger.warn("There was an error logging you in. Please login manually.");
        } catch (InternalErrorException e) {
            List<String> lines = StringUtils.wrapInLargeBox(List.of(
                "You are not logged in. Some commands are not available.",
                "-> You can use the help command (biblio help) to see the list of commands available to you."
            ));

            AppLogger.writeLines(AppLoggerColorEnum.ORANGE, lines);
            AppLogger.emptyLine();
        }

        if(args != null && args.length > 0) {
            runCLICommand(args);
            return;
        }

        AppLogger.writeLines(AppLoggerColorEnum.GREEN, BIBLIO_ART_LIST);
        AppLogger.emptyLine();
        // TODO: MENU
    }

    private static void runCLICommand(String[] args) {
        var userCliCommandNode = UserCliCommandFactory.makeUserCliCommandNode();
        var authCliCommandNode = new AuthCliCommandNode();

        var cliEntryPoint = new CliEntryPoint(authCliCommandNode, userCliCommandNode);
        ExitCode exitCode = cliEntryPoint.run(args);

        System.exit(exitCode.ordinal());
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