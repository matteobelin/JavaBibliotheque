package com.esgi.domain.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface AuthorService {
    AuthorEntity getAuthorById(int id) throws NotFoundException;
    AuthorEntity createAuthor(AuthorEntity author) throws ConstraintViolationException;
    void deleteAuthor(String name) throws NotFoundException, ConstraintViolationException;
    List<AuthorEntity> getAllAuthors();
    void updateAuthor(AuthorEntity author) throws ConstraintViolationException,NotFoundException;
    AuthorEntity getAuthorByName(String name) throws NotFoundException;
}
