package com.esgi.domain.users;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;
import com.esgi.domain.users.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userMapper = Mockito.mock(UserMapper.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }


    @Test
    public void get_User_By_Id_Should_Return_User() throws NotFoundException {
        // Arrange
        Integer userId = 1;
        UserEntity expectedUser = new UserEntity(userId, "email", true, "name", "test");
        UserModel returnedUser = new UserModel(userId, "email", true, "name", "test");

        Mockito.when(userRepository.getById(userId)).thenReturn(returnedUser);
        Mockito.when(userMapper.modelToEntity(Mockito.any())).thenReturn(expectedUser);

        // Act
        UserEntity result = userService.getUserById(userId);

        // Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expectedUser);
    }

    @Test
    public void create_User_Should_Not_Throw() {
        //Arrange
        UserEntity user = new UserEntity(0, "email", true, "name", "test");
        UserModel expectedUser = new UserModel(0, "email", true, "name", "test");
        Mockito.when(userMapper.entityToModel(user)).thenReturn(expectedUser);
        Mockito.doNothing().when(userRepository).create(expectedUser);

        //Act
        userService.createUser(user);
    }
}
