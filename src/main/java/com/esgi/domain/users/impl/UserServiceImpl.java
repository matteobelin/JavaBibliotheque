package com.esgi.domain.users.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserMapper;
import com.esgi.domain.users.UserService;
import com.esgi.domain.users.usecase.UserUseCase;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity getUserById(int id) throws NotFoundException {
        UserModel userModel = userRepository.getById(id);
        return userMapper.modelToEntity(userModel);
    }

    @Override
    public UserEntity getUserByEmail(String email) throws NotFoundException {
        UserModel userModel = userRepository.getByEmail(email);
        return userMapper.modelToEntity(userModel);
    }

    @Override
    public void createUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException {
        UserUseCase.validateUserInformationIsValid(user);

        UserModel userModel = userMapper.entityToModel(user);
        userRepository.create(userModel);
    }

    public void updateUser(UserEntity user) throws ConstraintViolationException, InvalidArgumentException, NotFoundException {
        UserUseCase.validateUserInformationIsValid(user);

        UserModel userModel = userMapper.entityToModel(user);
        userRepository.update(userModel);
    }
}
