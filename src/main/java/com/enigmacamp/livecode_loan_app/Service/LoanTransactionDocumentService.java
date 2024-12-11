package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;

import java.util.Optional;

public interface LoanTransactionDocumentService {
    void createFile(LoanTransactionRequest loanTransactionRequest, LoanTransaction id);
    LoanTransactionDocument findByTransaction(String transactionId);
    void deleteFile(String path, LoanTransactionDocument loanTransactionDocument);
}
