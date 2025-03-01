package com.esgi.domain.users;

import com.esgi.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends Entity {
    private String email;
    private String name;
    private boolean isAdmin;
    private String password;

    public UserEntity() {}
    protected UserEntity(int id) {
        super(id);
    }

    public UserEntity(Integer id, String email, boolean isAdmin, String name, String password) {
        super(id);
        this.email = email;
        this.isAdmin = isAdmin;
        this.name = name;
        this.password = password;
    }
}
