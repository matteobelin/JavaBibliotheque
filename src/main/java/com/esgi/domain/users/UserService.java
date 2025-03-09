package com.esgi.domain.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;

public interface UserService {
    UserEntity getUserById(int id) throws NotFoundException;
    UserEntity getUserByEmail(String email) throws NotFoundException;
    void createUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException;
    void updateUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException, NotFoundException;
}
