package com.esgi.domain.authors.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorMapper;
import com.esgi.domain.authors.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorEntity getAuthorById(int id) throws NotFoundException, InternalErrorException {
        AuthorModel authorModel = authorRepository.getById(id);
        return authorMapper.modelToEntity(authorModel);
    }

    public AuthorEntity getAuthorByName(String name) throws NotFoundException, InternalErrorException {
        AuthorModel authorModel = authorRepository.getByName(name);
        return authorMapper.modelToEntity(authorModel);
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) throws ConstraintViolationException, InternalErrorException {
        AuthorModel authorModel = authorMapper.entityToModel(authorEntity);
        authorRepository.create(authorModel);
        return authorMapper.modelToEntity(authorModel);
    }

    public void updateAuthor(AuthorEntity author) throws ConstraintViolationException, NotFoundException, InternalErrorException {

        AuthorModel authorModel = authorMapper.entityToModel(author);
        authorRepository.update(authorModel);
    }

    public void deleteAuthor(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        this.authorRepository.delete(name);
    }

    public List<AuthorEntity> getAllAuthors() throws InternalErrorException {
        List<AuthorModel> models = this.authorRepository.getAll();
        return authorMapper.modelsToEntities(models);
    }

}
