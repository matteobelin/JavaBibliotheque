package com.esgi.presentation.utils;

import com.esgi.core.exceptions.ActionDeniedException;
import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.ExitCode;

import java.util.ArrayList;
import java.util.List;

public final class LoanUtils {

    public static ExitCode handleBookLoanCommandException(Exception e) {
        AppLogger.error(e.getMessage());

        if (e instanceof ConstraintViolationException || e instanceof NotFoundException) {
            return ExitCode.ARGUMENT_INVALID;
        } else if (e instanceof ActionDeniedException) {
            return ExitCode.ACTION_DENIED;
        } else if (e instanceof BookLoanException) {
            return ExitCode.BOOK_LOAN_INVALID;
        }

        return ExitCode.INTERNAL_ERROR;
    }

    public static List<String> makeLoanTable(List<LoanEntity> loans) {
        var tableHeader = List.of(
                "#",
                "User Id",
                "Book Id",
                "Book",
                "Author",
                "Borrowed date",
                "Returned date"
        );
        var tableRows = new ArrayList<List<String>>();
        tableRows.add(tableHeader);

        for (int i = 0; i < loans.size(); i++) {
            var loan = loans.get(i);
            var tableRow = mapLoanToTableRow(i + 1, loan);
            tableRows.add(tableRow);
        }

        return StringUtils.makeTable(tableRows);
    }

    private static List<String> mapLoanToTableRow(Integer index, LoanEntity loan) {
        String returnedDate;
        if (loan.getEndDate() == null) {
            returnedDate = " - ";
        } else {
            returnedDate = loan.getEndDate().toString();
        }

        return List.of(
            index.toString(),
            String.valueOf(loan.getUserId()),
            String.valueOf(loan.getBook().getId()),
            loan.getBook().getTitle(),
            loan.getBook().getAuthor().getName(),
            loan.getStartDate().toString(),
            returnedDate
        );
    }
}
