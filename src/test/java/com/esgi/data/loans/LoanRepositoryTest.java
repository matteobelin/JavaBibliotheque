package com.esgi.data.loans;

import com.esgi.core.exceptions.BookLoanException;
import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.impl.LoanRepositoryImpl;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoanRepositoryTest {
    private LoanRepository loanRepostory;

    @BeforeAll
    public static void setUpBeforeAll() {
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp() {
        DatabaseTestHelper.resetTestDb();
        loanRepostory = new LoanRepositoryImpl();
    }

    @Test
    public void get_loan_by_id() throws NotFoundException, InternalErrorException {
        //Arrange
        Integer id = 1;
        //Act
        LoanModel loan = loanRepostory.getById(id);
        //Assert
        Assertions.assertThat(loan).isNotNull().hasFieldOrPropertyWithValue("id", loan.getId());
    }

    @Test
    public void get_loan_by_id_When_Not_Found_Should_Throw(){
        //Arrange
        Integer id = 0;
        //Atc Assert
        Assertions.assertThatThrownBy(() -> loanRepostory.getById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void get_loan_by_userId_should_Return_Loans() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        //Arrange
        Integer userId = 1;
        //Act
        List<LoanModel> loans = loanRepostory.getByUserId(userId);
        //Assert
        Assertions.assertThat(loans).isNotNull().hasSize(1);
    }

    @Test
    public void get_loan_by_userId_should_return_Empty_list() throws ConstraintViolationException, NotFoundException, InternalErrorException {
        //Arrange
        Integer userId = 0;
        //Act
        List<LoanModel> loans = loanRepostory.getByUserId(userId);
        // Assert
        Assertions.assertThat(loans).isNotNull().isEmpty();
    }

    @Test
    public void get_all_loans_should_Return_Loans(){
        //Act
        List<LoanModel> loans = loanRepostory.getAll();
        //Assert
        Assertions.assertThat(loans).isNotNull().hasSize(4);
    }

    @Test
    public void create_loan_should_save_Loan() throws ConstraintViolationException, NotFoundException, BookLoanException, InternalErrorException {
        //Arrange
        LoanModel loan = new LoanModel(
                null,
                2,
                3,
                null,
                null
        );
        //Act
        loanRepostory.create(loan);
        Integer id = loan.getId();
        LoanModel actual = loanRepostory.getById(id);
        //Assert
        Assertions.assertThat(actual).isNotNull().isEqualTo(loan);
    }

    @Test
    public void update_loan_should_update_Loan() throws ConstraintViolationException, NotFoundException, BookLoanException, InternalErrorException {
        //Arrange
        LoanModel loan = loanRepostory.getById(4);
        //Act
        loanRepostory.bookReturn(loan);
        Integer id = loan.getId();

        loanRepostory.getById(id);
        //Assert
        Assertions.assertThat(loanRepostory.getById(id)).isNotNull().isEqualTo(loan);
    }

    @Test
    public void get_active_loan_should_Return_Loans() throws InternalErrorException {
        //Act
        List<LoanModel> loans = loanRepostory.getCurrentLoan();
        //Assert
        Assertions.assertThat(loans).isNotNull().hasSize(1);
    }

    @Test
    public void get_active_loan_User_should_Return_Loans() throws InternalErrorException {
        //Act
        List<LoanModel> loans = loanRepostory.getCurrentLoanOfUser(2);
        //Assert
        Assertions.assertThat(loans).isNotNull().hasSize(1);
    }


    @Test
    public void get_active_loan_User_should_Return_Empty_Loan() throws InternalErrorException{
        //Act
        List<LoanModel> loans = loanRepostory.getCurrentLoanOfUser(1);
        //Assert
        Assertions.assertThat(loans).isNotNull().hasSize(0);

    }
}
