package com.esgi.domain.books;

import com.esgi.domain.Entity;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.genres.GenreEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookEntity extends Entity {
    private String title;
    private AuthorEntity author;
    private List<GenreEntity> genres;

    public BookEntity(Integer id,String title, AuthorEntity author, List<GenreEntity> genres) {
        super(id);
        this.title = title;
        this.author = author;
        this.genres = genres;
    }

    public BookEntity() {}
}
