package com.esgi.domain.users;

import com.esgi.data.users.UserModel;

public class UserMapper {
    public UserEntity modelToEntity(UserModel userModel) {
        return new UserEntity(
            userModel.getId(),
            userModel.getEmail(),
            userModel.isAdmin(),
            userModel.getName(),
            userModel.getPassword()
        );
    }

    public UserModel entityToModel(UserEntity userEntity) {
        return new UserModel(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.isAdmin(),
            userEntity.getName(),
            userEntity.getPassword()
        );
    }
}
