package com.esgi;

import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.cli.CliEntryPoint;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.users.UserCliCommandNode;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserServiceFactory.makeUserService();

        if(args != null && args.length > 0) {
            AddUserCliCommandNode addUserCliCommandNode = new AddUserCliCommandNode(userService);
            UserCliCommandNode userCliCommandNode = new UserCliCommandNode(addUserCliCommandNode);

            var cliEntryPoint = new CliEntryPoint(userCliCommandNode);
            ExitCode exitCode = cliEntryPoint.run(args);
            System.exit(exitCode.ordinal());
            return;
        }

        // TODO: MENU
    }
}