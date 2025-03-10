package com.esgi.domain.authors;

import com.esgi.data.authors.AuthorRepository;
import com.esgi.data.authors.AuthorRepositoryFactory;
import com.esgi.domain.authors.impl.AuthorServiceImpl;

public class AuthorServiceFactory {
    public static AuthorService makeAuthorService() {
        AuthorRepository authorRepository = AuthorRepositoryFactory.makeAuthorsRepository();
        AuthorMapper authorMapper = new AuthorMapper();
        return new AuthorServiceImpl(authorRepository, authorMapper);
    }

}
