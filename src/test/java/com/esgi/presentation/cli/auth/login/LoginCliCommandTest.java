package com.esgi.presentation.cli.auth.login;

import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginCliCommandTest {

    @InjectMocks
    LoginCliCommandNode loginCliCommandNode;

    @Test
    public void should_return_ARGUMENT_MISSING_CODE_when_value_is_missing() {
        // Arrange
        String[] args = { "email" };

        // Act
        var exitCode = this.loginCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_MISSING);
    }
}
