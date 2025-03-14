package com.esgi.domain.loans;

import com.esgi.domain.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class LoanEntity extends Entity {
    private int userId;
    private int bookId;
    private Date startDate;
    private Date endDate;

    public LoanEntity(int id, int userId,int bookId, Date startDate, Date endDate) {
        super(id);
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
