package com.esgi.domain.authors;

import com.esgi.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorEntity extends Entity {
    private String name;

    public AuthorEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
