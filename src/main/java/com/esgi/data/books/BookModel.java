package com.esgi.data.books;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookModel extends Model {
    private String title;
    private Integer authorId;
    private List<Integer> genreIds;

    public BookModel(Integer id,String title, Integer authorId, List<Integer> genreIds) {
        super(id);
        this.title = title;
        this.authorId = authorId;
        this.genreIds = genreIds;
    }

}
