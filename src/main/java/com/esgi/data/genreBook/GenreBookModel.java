package com.esgi.data.genreBook;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenreBookModel extends Model {

    private Integer genreId;
    private Integer bookId;

    public GenreBookModel(Integer genreId, Integer bookId) {
        this.genreId = genreId;
        this.bookId = bookId;
    }

    public GenreBookModel() {}
}
