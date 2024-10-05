package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;

public interface LoanTransactionDocumentService {
    void createFile(LoanTransactionRequest loanTransactionRequest);
    LoanTransactionDocument findByCustomer(String customerId);
    void deleteFile(String path, LoanTransactionDocument loanTransactionDocument);
}
