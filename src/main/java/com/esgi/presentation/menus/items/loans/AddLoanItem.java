package com.esgi.presentation.menus.items.loans;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.loans.LoanEntity;
import com.esgi.domain.loans.LoanService;
import com.esgi.domain.loans.LoanServiceFactory;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.menus.Menu;
import com.esgi.presentation.menus.items.MenuItem;

import java.util.Scanner;

public class AddLoanItem implements MenuItem {
    @Override
    public String getTitle() {
        return "add a loan";
    }

    @Override
    public void execute(Menu menu) throws Exception {
        Scanner scanner = new Scanner(System.in);
        var loan = new LoanEntity();
        var book = new BookEntity();

        AppLogger.info("Enter a user email: ");
        String email = scanner.nextLine();
        UserService userService = UserServiceFactory.getUserService();

        try {
            UserEntity user = userService.getUserByEmail(email);
        } catch (NotFoundException e) {
            AppLogger.info("User not found");
            execute(menu);
            return;
        }

        AppLogger.info("Enter a book ID: ");
        String input = scanner.nextLine();

        try {
            book.setId(Integer.parseInt(input));
        }
        catch (NumberFormatException e) {
            AppLogger.error("Invalid input");
            execute(menu);
            return;
        }
        loan.setBook(book);


        LoanService loanService = LoanServiceFactory.getLoanService();
        loanService.createLoan(loan);

        AppLogger.success("Loan added");
        menu.display();
    }

    @Override
    public boolean needsAdmin() {
        return true;
    }
}
