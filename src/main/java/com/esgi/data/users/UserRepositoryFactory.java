package com.esgi.data.users;

import com.esgi.data.users.impl.UserRepositoryImpl;

public class UserRepositoryFactory {
    public static UserRepository makeUserRepository() {
        return new UserRepositoryImpl();
    }
}
