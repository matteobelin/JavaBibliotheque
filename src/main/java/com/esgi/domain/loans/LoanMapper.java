package com.esgi.domain.loans;

import com.esgi.data.loans.LoanModel;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LoanMapper {

    public LoanEntity modelToEntity(LoanModel loan){
        return new LoanEntity(
                loan.getId(),
                loan.getUserId(),
                loan.getBookId(),
                loan.getStartDate(),
                loan.getEndDate()
        );
    }

    public LoanModel entityToModel(LoanEntity loan){
        return new LoanModel(
                loan.getId(),
                loan.getUserId(),
                loan.getBookId(),
                (Date) loan.getStartDate(),
                (Date) loan.getEndDate()
        );
    }

    public List<LoanEntity> modelToEntity(List<LoanModel> loans){
        List<LoanEntity> loansEntity = new ArrayList<>();
        for(LoanModel loan : loans){
            loansEntity.add(this.modelToEntity(loan));
        }
        return loansEntity;
    }
}
