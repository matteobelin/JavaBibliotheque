package com.esgi.data.books;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookModel extends Model {
    private String title;
    private Integer author_id;
    private List<Integer> genre_ids;

    public BookModel(Integer id,String title, Integer author_id, List<Integer> genre_ids) {
        super(id);
        this.title = title;
        this.author_id = author_id;
        this.genre_ids = genre_ids;
    }

}
