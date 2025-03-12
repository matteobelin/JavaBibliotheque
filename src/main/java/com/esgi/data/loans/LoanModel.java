package com.esgi.data.loans;

import com.esgi.data.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoanModel extends Model {
    private Integer userId;
    private Integer bookId;
    private Date startDate;
    private Date endDate;

    public LoanModel(Integer id, Integer userId, Integer bookId, Date startDate, Date endDate) {
        super(id);
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
