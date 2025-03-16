package com.esgi.presentation.cli.users.add;

import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;

public final class AddUserCliCommandFactory {

    public static AddUserCliCommandNode makeAddUserCliCommandNode() {
        UserService userService = UserServiceFactory.getUserService();

        return new AddUserCliCommandNode(userService);
    }
}
