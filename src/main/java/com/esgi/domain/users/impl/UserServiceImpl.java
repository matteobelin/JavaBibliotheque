package com.esgi.domain.users.impl;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserMapper;
import com.esgi.domain.users.UserService;

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
    public UserEntity getUserByEmail(String email) {
        throw new RuntimeException("Not implemented yet");
    }
}
