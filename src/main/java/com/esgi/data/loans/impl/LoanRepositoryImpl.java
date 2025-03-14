package com.esgi.data.loans.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.loans.LoanModel;
import com.esgi.data.loans.LoanRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class LoanRepositoryImpl extends Repository<LoanModel> implements LoanRepository {
    public static final String TABLE_NAME = "loans";


    public LoanRepositoryImpl(){
        super(TABLE_NAME);
    }
    @Override
    protected LoanModel parseSQLResult(ResultSet result) throws SQLException {
        return new LoanModel(
                result.getInt("id"),
                result.getInt("user_id"),
                result.getInt("book_id"),
                result.getDate("start_date"),
                result.getDate("end_date")
        );
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(LoanModel loan) {
        return Map.of(
                "user_id", (statement, index) -> statement.setInt(index, loan.getUserId()),
                "book_id",(statement, index) -> statement.setInt(index, loan.getBookId()),
                "start_date",(statement, index) -> statement.setDate(index,loan.getStartDate())
                );
    }

    private Map<String, SQLColumnValueBinder> getEndDate(LoanModel loan) {
        return Map.of(
                "end_date", (statement, index) -> statement.setDate(index, loan.getEndDate())
        );
    }

    @Override
    public void create(LoanModel loanModel) throws ConstraintViolationException, NotFoundException {
        if(validateBookLoan(loanModel)){
            throw new IllegalStateException("Book is already loaned");
        };
        loanModel.setStartDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        var columns = getColumnValueBinders(loanModel);
        try {
            super.executeCreate(columns,loanModel);
        }catch (SQLException e){
            handleSQLException(e);
        }
    };

    @Override
    public void bookReturn(LoanModel loanModel) throws ConstraintViolationException, NotFoundException {
        if(!validateBookLoan(loanModel)){
            throw new IllegalStateException("Book is not loaned");
        };
        loanModel.setEndDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        var columns = getEndDate(loanModel);
        try{
            super.executeUpdate(columns,loanModel.getId());
        }catch (SQLException e){
            handleSQLException(e);
        }
    }

    public boolean validateBookLoan(LoanModel loanModel) throws ConstraintViolationException, NotFoundException {
        List<LoanModel> loans = getAllLoansBook();
        return loans.stream()
                .anyMatch(loan -> loan.getBookId().equals(loanModel.getBookId()));
    }


    private void handleSQLException(SQLException e) throws ConstraintViolationException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);
        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new RuntimeException(e);
        }
        if (optionalExceptionType.get() == CONSTRAINT_NOTNULL) {
                throw new ConstraintViolationException("A required field of the genre is missing.");
        }
    }

    public List<LoanModel> getByUserId(Integer userId) throws NotFoundException {
        return getAllByColumn("user_id",userId);
    }

    public List<LoanModel> getAllLoansBook() throws NotFoundException {
        return getAllByColumn("end_date",null);
    }


}
