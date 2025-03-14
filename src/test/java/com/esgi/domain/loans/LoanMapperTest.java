package com.esgi.domain.loans;

import com.esgi.data.loans.LoanModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class LoanMapperTest {
    private LoanMapper loanMapper;

    @BeforeEach
    public void setUp() {loanMapper = new LoanMapper();}

    @Test
    public void loanModel_to_Entity(){
        //Arrange
        LoanModel loanModel = makeLoanModel();
        LoanEntity loanEntity = makeLoanEntity();
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

    @Test void loanModelList_to_EntityList(){
        //Arrange
        List<LoanModel> loanModel = makeLoanModelList();
        List<LoanEntity> loanEntities = makeLoanEntityList();
        //Act
        List<LoanEntity> loans = loanMapper.modelToEntity(loanModel);
        //Assert
        Assertions.assertThat(loans).isEqualTo(loanEntities);

    }

    private LoanModel makeLoanModel() {
        return new LoanModel(2, 1,2, java.sql.Date.valueOf("2025-01-10"),null);
    }

    private LoanEntity makeLoanEntity() {
        return new LoanEntity(2, 1,2,java.sql.Date.valueOf("2025-01-10"),null);
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
