package com.esgi.data.users;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface UserRepository {
   UserModel getById(Integer id) throws NotFoundException;
   void create(UserModel user) throws ConstraintViolationException;
}
