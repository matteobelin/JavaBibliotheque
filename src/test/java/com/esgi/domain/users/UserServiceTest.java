package com.esgi.domain.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;
import com.esgi.domain.users.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)


public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;


    @Test
    public void get_User_By_Id_Should_Return_User() throws NotFoundException, InternalErrorException {
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
    public void create_User_Should_Not_Throw() throws ConstraintViolationException, InvalidArgumentException, InternalErrorException {
        //Arrange
        UserEntity user = new UserEntity(0, "email@gmail.com", true, "name", "test");
        UserModel expectedUser = new UserModel(0, "email@gmail.com", true, "name", "test");
        Mockito.when(userMapper.entityToModel(user)).thenReturn(expectedUser);
        Mockito.doNothing().when(userRepository).create(expectedUser);

        //Act
        userService.createUser(user);
    }

    @Test
    public void updateUser_should_not_throw() throws NotFoundException, ConstraintViolationException, InvalidArgumentException, InternalErrorException {
        // Arrange
        UserEntity user = new UserEntity(0, "email@gmail.com", true, "name", "test");
        UserModel expectedUser = new UserModel(0, "email@gmail.com", true, "name", "test");
        Mockito.when(userMapper.entityToModel(user)).thenReturn(expectedUser);
        Mockito.doNothing().when(userRepository).update(expectedUser);

        //Act
        userService.updateUser(user);
    }

    @Test
    public void updateUser_should_throw_InvalidArgumentException_when_email_is_invalid()  {
        // Arrange
        UserEntity user = new UserEntity(0, "email@gmail", true, "name", "test");

        // Act - Assert
        Assertions.assertThatThrownBy(() -> userService.updateUser(user))
                .isInstanceOf(InvalidArgumentException.class);
    }
}
