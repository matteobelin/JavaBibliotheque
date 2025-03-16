package com.esgi.presentation.cli.books.unborrowed;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookService;
import com.esgi.domain.loans.LoanService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.BookUtils;

public class UnborrowedBookCliCommandeNode extends CliCommandNode {
    public static final String NAME = "unborrowed";

    public static final String DESCRIPTION = "Lists all books unborrowed in the library";

    private final BookService bookService;
    private final LoanService loanService;

    public UnborrowedBookCliCommandeNode(BookService bookService, LoanService loanService) {
        super(NAME, DESCRIPTION);
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @Override
    public ExitCode run(String[] args) {
        try {
            var books = this.bookService.getAllBooks();

            var loans = this.loanService.getCurrentLoan();

            books.removeIf(book ->
                    loans.stream()
                            .anyMatch(loan -> loan.getBook().equals(book))
            );

            var table = BookUtils.makeBookTable(books);

            AppLogger.emptyLine();
            AppLogger.info(table);
        } catch (NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR; // should not happen, could be a foreign key not respected
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }


}
