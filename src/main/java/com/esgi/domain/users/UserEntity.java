package com.esgi.domain.users;

import com.esgi.domain.Entity;
import lombok.Data;

@Data
public class UserEntity extends Entity {
    private String email;
    private String name;
    private boolean isAdmin;

    public UserEntity() {}
    protected UserEntity(int id) {
        super(id);
    }

    public UserEntity(Integer id, String email, boolean isAdmin, String name) {
        super(id);
        this.email = email;
        this.isAdmin = isAdmin;
        this.name = name;
    }
}
