package com.esgi.presentation.cli.auth.signin;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.domain.users.UserService;
import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class SigninCliCommandNodeTest {

    @InjectMocks
    private SigninCliCommandNode signinCliCommandNode;

    @Mock
    private UserService userService;


    @Test
    public void should_return_OK() throws InvalidArgumentException, ConstraintViolationException, InternalErrorException {
        // Arrange
        String[] args = new String[] {"email@email.com", "password"};

        doNothing().when(userService).createUser(any());

        // Act
        var exitCode = this.signinCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_return_ARGUMENT_MISSING_when_missing_argument() throws InternalErrorException {
        // Arrange
        String[] args = new String[] {"email@email.com"};

        // Act
        var exitCode = this.signinCliCommandNode.run(args);

        //Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_MISSING);
    }
}
