package com.esgi.data.users;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserModel extends Model {
    private String email;
    private boolean isAdmin;
    private String name;
    private String password;

    public UserModel(Integer id, String email, boolean isAdmin, String name, String password) {
        super(id);
        this.email = email;
        this.isAdmin = isAdmin;
        this.name = name;
        this.password = password;
    }
}
