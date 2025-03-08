package com.esgi.presentation.cli.users;

import com.esgi.presentation.cli.users.add.AddUserCliCommandFactory;

public final class UserCliCommandFactory {

    public static UserCliCommandNode makeUserCliCommandNode() {
        var addUserCliCommandNode = AddUserCliCommandFactory.makeAddUserCliCommandNode();

        return new UserCliCommandNode(addUserCliCommandNode);
    }
}
