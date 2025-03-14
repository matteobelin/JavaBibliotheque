package com.esgi.domain.genres;

import com.esgi.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class GenreEntity extends Entity {
    private String name;

    public GenreEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
    public GenreEntity() {}
}
