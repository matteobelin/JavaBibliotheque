package com.esgi.domain.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface AuthorService {
    AuthorEntity getAuthorById(int id) throws NotFoundException, InternalErrorException;
    AuthorEntity createAuthor(AuthorEntity author) throws ConstraintViolationException, InternalErrorException;
    void deleteAuthor(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException;
    List<AuthorEntity> getAllAuthors() throws InternalErrorException;
    void updateAuthor(AuthorEntity author) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    AuthorEntity getAuthorByName(String name) throws NotFoundException, InternalErrorException;
}
