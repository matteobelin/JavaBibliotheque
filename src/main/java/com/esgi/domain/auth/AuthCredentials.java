package com.esgi.domain.auth;

import java.io.Serializable;

public record AuthCredentials(String email, String password, boolean stayLoggedIn) implements Serializable {
}
