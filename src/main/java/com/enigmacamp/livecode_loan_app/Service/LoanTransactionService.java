package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;

import java.util.List;

public interface LoanTransactionService {

    List<LoanTransaction> findAll();
    LoanTransaction createLoanTransaction(LoanTransactionRequest loanTransactionRequest);
    LoanTransaction findById(String id);
    LoanTransaction approveLoanTransaction(String id, ApproveTransactionRequest request);
    LoanTransaction rejectLoanTransaction(String id, ApproveTransactionRequest request);
    LoanTransaction updateTransactionDetail(String id);

}
