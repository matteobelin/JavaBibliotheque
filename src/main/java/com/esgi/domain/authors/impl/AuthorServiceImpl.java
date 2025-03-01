package com.esgi.domain.authors.impl;

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

    @Override
    public AuthorEntity getAuthorById(int id) throws NotFoundException{
        AuthorModel authorModel = authorRepository.getById(id);
        return authorMapper.modelToEntity(authorModel);
    }



}
