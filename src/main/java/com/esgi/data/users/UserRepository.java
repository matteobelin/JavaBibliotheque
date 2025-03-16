package com.esgi.data.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;


public interface UserRepository {
   UserModel getById(Integer id) throws NotFoundException, InternalErrorException;
   UserModel getByEmail(String email) throws NotFoundException, InternalErrorException;
   void create(UserModel user) throws ConstraintViolationException;
   void update(UserModel user) throws NotFoundException, ConstraintViolationException;
   void delete(Integer id) throws NotFoundException, ConstraintViolationException, InternalErrorException;
}
