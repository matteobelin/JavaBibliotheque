package com.esgi.domain.users;

import com.esgi.data.users.UserRepository;
import com.esgi.data.users.UserRepositoryFactory;
import com.esgi.domain.users.impl.UserServiceImpl;

public class UserServiceFactory {

    private static UserService userService;

    public static UserService getUserService() {
        if (userService == null) {
            userService = makeUserService();
        }

        return userService;
    }

    private static UserService makeUserService() {
        UserRepository userRepository = UserRepositoryFactory.makeUserRepository();
        UserMapper userMapper = new UserMapper();
        return new UserServiceImpl(userRepository, userMapper);
    }
}
