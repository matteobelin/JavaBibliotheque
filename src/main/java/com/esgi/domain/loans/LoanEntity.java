package com.esgi.domain.loans;

import com.esgi.domain.Entity;
import com.esgi.domain.books.BookEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class LoanEntity extends Entity {
    private int userId;
    private BookEntity book;
    private Date startDate;
    private Date endDate;

    public LoanEntity(int id, int userId, BookEntity book, Date startDate, Date endDate) {
        super(id);
        this.userId = userId;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LoanEntity() {}
}
