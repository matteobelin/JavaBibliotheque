package com.esgi.domain.authors.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorMapper;
import com.esgi.domain.authors.AuthorService;


public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorEntity getAuthorById(int id) throws NotFoundException{
        AuthorModel authorModel = authorRepository.getById(id);
        return authorMapper.modelToEntity(authorModel);
    }

    @Override
    public void createAuthor(AuthorEntity authorEntity) throws ConstraintViolationException {
        AuthorModel authorModel = authorMapper.entityToModel(authorEntity);
        authorRepository.create(authorModel);
    }

    public void updateAuthor(AuthorEntity author) throws ConstraintViolationException, NotFoundException {

        AuthorModel authorModel = authorMapper.entityToModel(author);
        authorRepository.update(authorModel);
    }

    public void deleteAuthor(int id) throws NotFoundException,IllegalStateException {
        authorRepository.delete(id);
    }



}
