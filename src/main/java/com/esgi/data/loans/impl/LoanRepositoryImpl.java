package com.esgi.data.loans.impl;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.loans.LoanModel;
import com.esgi.data.loans.LoanRepository;
import com.esgi.data.sql.SQLColumnValue;
import com.esgi.data.sql.SQLComparator;
import com.esgi.data.sql.SQLNullValue;
import com.esgi.data.sql.SQLWhereCondition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class LoanRepositoryImpl extends Repository<LoanModel> implements LoanRepository {
    public static final String TABLE_NAME = "loans";

    private static final String USER_ID_COLUMN = "user_id";
    private static final String BOOK_ID_COLUMN = "book_id";
    private static final String START_DATE_COLUMN = "start_date";
    private static final String END_DATE_COLUMN = "end_date";

    public LoanRepositoryImpl(){
        super(TABLE_NAME);
    }
    @Override
    protected LoanModel parseSQLResult(ResultSet result) throws SQLException {
        return new LoanModel(
            result.getInt("id"),
            result.getInt(USER_ID_COLUMN),
            result.getInt(BOOK_ID_COLUMN),
            result.getDate(START_DATE_COLUMN),
            result.getDate(END_DATE_COLUMN)
        );
    }

    @Override
    public void create(LoanModel loanModel) throws ConstraintViolationException, NotFoundException, BookLoanException, InternalErrorException {
        loanModel.setStartDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        var columns = getColumnValueBinders(loanModel);
        try {
            var id = super.executeCreate(columns);
            loanModel.setId(id);
        }catch (SQLException e){
            handleSQLException(e);
        }
    }

    private List<SQLColumnValue<?>> getColumnValueBinders(LoanModel loan) {
        return List.of(
          new SQLColumnValue<>(USER_ID_COLUMN, loan.getUserId()),
          new SQLColumnValue<>(BOOK_ID_COLUMN, loan.getBookId()),
          new SQLColumnValue<>(START_DATE_COLUMN, loan.getStartDate())
        );
    }

    @Override
    public void bookReturn(LoanModel loan) throws ConstraintViolationException, NotFoundException, InternalErrorException {
        loan.setEndDate(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        List<SQLColumnValue<?>> conditions = List.of(
            new SQLColumnValue<>(END_DATE_COLUMN, loan.getEndDate())
        );

        try{
            super.executeUpdate(conditions, loan.getId());
        }catch (SQLException e){
            handleSQLException(e);
        }
    }

    @Override
    public boolean isBookBorrowed(Integer bookId) {
        try {
            var conditions = List.of(
                SQLWhereCondition.makeEqualCondition(BOOK_ID_COLUMN, bookId),
                SQLWhereCondition.makeIsNullCondition(END_DATE_COLUMN, Types.DATE)
            );
            super.findFirstWhere(conditions);
            return true;
        } catch (NotFoundException | InternalErrorException e) {
            return false;
        }
    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException, InternalErrorException {
        var exceptionType = super.parseSqlException(e);
        if (exceptionType == CONSTRAINT_NOTNULL) {
            throw new ConstraintViolationException("A required field of the genre is missing.");
        }
    }

    public LoanModel findLoanByUserIdAndBookId(Integer userId, Integer bookId)
            throws NotFoundException, InternalErrorException {
        return super.findFirstWhere(List.of(
            SQLWhereCondition.makeEqualCondition(USER_ID_COLUMN, userId),
            SQLWhereCondition.makeEqualCondition(BOOK_ID_COLUMN, bookId)
        ));
    }

    public List<LoanModel> getByUserId(Integer userId) throws InternalErrorException {
        return getAllWhereColumnEquals(USER_ID_COLUMN, userId);
    }

    @Override
    public List<LoanModel> getCurrentLoanOfUser(Integer userId) throws InternalErrorException {
        var conditions = List.of(
            SQLWhereCondition.makeEqualCondition(USER_ID_COLUMN, userId),
            new SQLWhereCondition<>(END_DATE_COLUMN, SQLComparator.IS, new SQLNullValue(Types.DATE))
        );
        return super.getAllWhere(conditions);
    }

    public List<LoanModel> getCurrentLoan() throws InternalErrorException {
        List<SQLWhereCondition<?>> conditions  = List.of(
                new SQLWhereCondition(END_DATE_COLUMN, SQLComparator.IS, new SQLNullValue(Types.DATE))
        );
        return super.getAllWhere(conditions);
    }
}
