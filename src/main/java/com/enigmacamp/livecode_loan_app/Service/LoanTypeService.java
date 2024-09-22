package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.LoanTypeRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanType;

import java.util.List;

public interface LoanTypeService {
    List<LoanType> findAll();
    LoanType findById(String id);
    LoanType create(LoanTypeRequest request);
    LoanType update(LoanType request);
    void deleteById(String id);
}
