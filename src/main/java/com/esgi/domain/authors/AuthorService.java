package com.esgi.domain.authors;

import com.esgi.core.exceptions.NotFoundException;

public interface AuthorService {
    AuthorEntity getAuthorById(int id) throws NotFoundException;
}
