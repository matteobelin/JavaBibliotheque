package com.esgi.domain.users;

import com.esgi.core.exceptions.NotFoundException;

public interface UserService {
    UserEntity getUserById(int id) throws NotFoundException;
    UserEntity getUserByEmail(String email);
}
