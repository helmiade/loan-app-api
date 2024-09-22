package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;

import java.util.List;

public interface LoanTransactionService {

    List<LoanTransaction> findAll();

}
