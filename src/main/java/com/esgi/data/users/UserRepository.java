package com.esgi.data.users;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.users.UserEntity;

public interface UserRepository {
   UserModel getById(Integer id) throws NotFoundException;
}
