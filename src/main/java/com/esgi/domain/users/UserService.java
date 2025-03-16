package com.esgi.domain.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;

public interface UserService {
    UserEntity getUserById(int id) throws NotFoundException, InternalErrorException;
    UserEntity getUserByEmail(String email) throws NotFoundException, InternalErrorException;
    void createUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException, InternalErrorException;
    void updateUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException, NotFoundException, InternalErrorException;
    void deleteUser(int id) throws NotFoundException, ConstraintViolationException, InternalErrorException;
}
