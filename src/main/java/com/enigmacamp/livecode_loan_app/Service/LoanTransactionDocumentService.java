package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;
import org.springframework.core.io.Resource;

public interface LoanTransactionDocumentService {
    LoanTransactionDocument createFile(LoanTransactionRequest loanTransactionRequest);
    LoanTransactionDocument findByCustomer(String customerId);
    void deleteFile(String path, LoanTransactionDocument loanTransactionDocument);
}
