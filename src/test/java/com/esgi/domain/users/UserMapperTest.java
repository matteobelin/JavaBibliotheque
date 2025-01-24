package com.esgi.domain.users;

import com.esgi.data.users.UserModel;
import org.assertj.core.api.Assertions;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
     userMapper = new UserMapper();
    }

    @Test
    public void userModel_to_UserEntity() {
        // Arrange
        UserModel userModel = makeUserModel();
        UserEntity expectedUserEntity = makeUserEntity();

        // Act
        UserEntity result = userMapper.modelToEntity(userModel);

        // Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expectedUserEntity);
    }

    private UserModel makeUserModel() {
        return new UserModel(1, "email", true, "name");
    }

    private UserEntity makeUserEntity() {
        return new UserEntity(1, "email", true, "name");
    }
}
