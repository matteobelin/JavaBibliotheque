package com.esgi.data.authors;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorModel extends Model {
    private String name;

    public AuthorModel(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
