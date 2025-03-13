package com.esgi.domain.authors;

import com.esgi.data.authors.AuthorRepository;
import com.esgi.data.authors.AuthorRepositoryFactory;
import com.esgi.domain.authors.impl.AuthorServiceImpl;

public class AuthorServiceFactory {
    private static AuthorService authorService;

    public static AuthorService getAuthorService() {
        if (authorService == null) {
            authorService = makeAuthorService();
        }
        return authorService;
    }

    public static AuthorService makeAuthorService() {
        AuthorRepository authorRepository = AuthorRepositoryFactory.makeAuthorsRepository();
        AuthorMapper authorMapper = new AuthorMapper();
        return new AuthorServiceImpl(authorRepository, authorMapper);
    }

}
