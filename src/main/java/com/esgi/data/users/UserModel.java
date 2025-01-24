package com.esgi.data.users;

import com.esgi.data.Model;
import lombok.Data;


@Data
public class UserModel extends Model {
    private String email;
    private boolean isAdmin;
    private String name;

    public UserModel() {}
    public UserModel(Integer id, String email, boolean isAdmin, String name) {
        super(id);
        this.email = email;
        this.isAdmin = isAdmin;
        this.name = name;
    }
}
