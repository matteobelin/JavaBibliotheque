package com.esgi.data.authors;

import com.esgi.data.authors.impl.AuthorRepositoryImpl;

public class AuthorRepositoryFactory {
    public static AuthorRepository makeAuthorsRepository() {
        return new AuthorRepositoryImpl();
    }
}
