package com.esgi.data.authors;

import com.esgi.core.exceptions.NotFoundException;

public interface AuthorRepository {
    AuthorModel getById(Integer id) throws NotFoundException;
}
