package com.esgi.domain.loans;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.loans.LoanModel;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class LoanMapperTest {
    @InjectMocks
    private LoanMapper loanMapper;

    @Mock
    private BookService bookService;

    @Test
    public void loanModel_to_Entity() throws NotFoundException, InternalErrorException {
        //Arrange
        LoanModel loanModel = makeLoanModel();
        LoanEntity loanEntity = makeLoanEntity();

        Mockito.when(bookService.getBookById(anyInt()))
                .thenReturn(loanEntity.getBook());

        //Act
        LoanEntity loan = loanMapper.modelToEntity(loanModel);

        //Assert
        Assertions.assertThat(loan).isEqualTo(loanEntity);

    }

    @Test
    public void loanEntity_to_Model(){
        //Arrange
        LoanModel loanModel = makeLoanModel();
        LoanEntity loanEntity = makeLoanEntity();
        //Act
        LoanModel loan = loanMapper.entityToModel(loanEntity);
        //Assert
        Assertions.assertThat(loan).isEqualTo(loanModel);

    }

    @Test void loanModelList_to_EntityList() throws NotFoundException, InternalErrorException {
        //Arrange
        List<LoanModel> loanModel = makeLoanModelList();
        List<LoanEntity> loanEntities = makeLoanEntityList();

        Mockito.when(bookService.getBookById(anyInt()))
                .thenReturn(loanEntities.get(0).getBook());

        //Act
        List<LoanEntity> loans = loanMapper.modelToEntity(loanModel);
        //Assert
        Assertions.assertThat(loans).isEqualTo(loanEntities);

    }

    private LoanModel makeLoanModel() {
        return new LoanModel(2, 1,2, java.sql.Date.valueOf("2025-01-10"),null);
    }

    private LoanEntity makeLoanEntity() {
        var book = new BookEntity();
        book.setId(2);
        return new LoanEntity(2, 1, book, java.sql.Date.valueOf("2025-01-10"),null);
    }

    private List<LoanEntity> makeLoanEntityList() {
        List<LoanEntity> loanEntityList = new ArrayList<>();
        loanEntityList.add(makeLoanEntity());
        return loanEntityList;
    }
    private List<LoanModel> makeLoanModelList() {
        List<LoanModel> loanModelList = new ArrayList<>();
        loanModelList.add(makeLoanModel());
        return loanModelList;
    }
}
