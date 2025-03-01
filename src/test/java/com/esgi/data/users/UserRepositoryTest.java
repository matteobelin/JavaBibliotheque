package com.esgi.data.users;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.users.impl.UserRepositoryImpl;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp() {
        DatabaseTestHelper.resetTestDb();
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void get_User_By_Id_Should_Return_User() throws NotFoundException {
        // Arrange
        Integer userId = 1;

        // Act
        UserModel actual = userRepository.getById(userId);

        // Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", actual.getId());
    }

    @Test
    public void get_User_By_Id_When_Not_Found_Should_Throw() {
        // Arrange
        Integer userId = 0;

        // Act - Assert
        Assertions.assertThatThrownBy(() -> userRepository.getById(userId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_User_Should_Save_User() {
        //Arrange
        UserModel user = new UserModel(
            null,
            "test@email.com",
            false,
            "name",
            "password"
        );

        //Act - Assert
        userRepository.create(user);
    }

    @Test
    void create_User_With_Existing_Email_Should_Throw() {
        //Arrange
        UserModel user = new UserModel(
                null,
                "test@email.com",
                false,
                "name",
                "password"
        );

        //Act
        userRepository.create(user);

        //Assert
        Assertions.assertThatThrownBy(() -> userRepository.create(user));
    }

    @Test
    void create_User_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        UserModel user = new UserModel(
                null,
                "test@email.com",
                false,
                "name",
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> userRepository.create(user));
    }
}
