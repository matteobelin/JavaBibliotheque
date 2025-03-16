package com.esgi.data.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
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
    public void get_User_By_Id_Should_Return_User() throws NotFoundException, InternalErrorException {
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
    void create_User_Should_Save_User() throws ConstraintViolationException, NotFoundException, InternalErrorException {
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
        Integer userId = user.getId();
        UserModel actual = userRepository.getById(userId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(UserModel::getEmail)
                .isEqualTo(user.getEmail());

    }

    @Test
    void create_User_With_Existing_Email_Should_Throw() throws ConstraintViolationException {
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

    @Test
    void update_should_update_the_user() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        //Arrange
        UserModel editedUser = new UserModel(
            2,
            "email.notindb@example.com",
            true,
            "name",
            null
        );

        // Act
        this.userRepository.update(editedUser);

        // Assert
        UserModel userFromDb = userRepository.getById(editedUser.getId());
        Assertions.assertThat(userFromDb)
                .hasFieldOrPropertyWithValue("email", editedUser.getEmail())
                .hasFieldOrPropertyWithValue("name", editedUser.getName())
                .hasFieldOrPropertyWithValue("isAdmin", editedUser.isAdmin())
                .hasFieldOrPropertyWithValue("id", 2);
    }

    @Test
    void update_should_not_change_password() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        String existingUserPassword = "test";

        UserModel editedUser = new UserModel(2,
        "new.email@example.com",
        true,
        "New Name",
    "new password"
        );

        // Act
        userRepository.update(editedUser);

        // Assert
        UserModel userFromDb = userRepository.getById(editedUser.getId());
        Assertions.assertThat(userFromDb)
                .hasFieldOrPropertyWithValue("password", existingUserPassword);
    }


    @Test
    void update_should_throw_NotFoundException_when_user_id_doesnt_exist() {
        //Arrange
        UserModel user = new UserModel(
                20000,
                "user1@example.com",
                false,
                "name",
                null
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.userRepository.update(user))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_should_throw_ConstraintViolationException_when_user_email_taken() {
        //Arrange
        UserModel user = new UserModel(
                2,
                "user2@example.com",
                false,
                "name",
                null
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.userRepository.update(user))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete_should_delete_user() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        int id = 1;

        // Act
        this.userRepository.delete(id);

        // Assert
        Assertions.assertThatThrownBy(() -> this.userRepository.getById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_should_throw_NotFoundException_when_id_not_in_db() throws NotFoundException {
        // Arrange
        int id = 100000;

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.userRepository.delete(id))
                .isInstanceOf(NotFoundException.class);
    }
}
