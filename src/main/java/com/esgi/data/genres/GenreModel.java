package com.esgi.data.genres;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GenreModel extends Model {
    private String name;

    public GenreModel(Integer id, String name) {
        super(id);
        this.name = name;
    }

}
