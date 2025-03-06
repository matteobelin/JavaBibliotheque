package com.esgi.domain.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface AuthorService {
    AuthorEntity getAuthorById(int id) throws NotFoundException;
    void createAuthor(AuthorEntity author) throws ConstraintViolationException;
}
