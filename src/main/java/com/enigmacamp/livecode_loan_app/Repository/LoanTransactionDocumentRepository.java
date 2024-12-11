package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTransactionDocumentRepository extends JpaRepository<LoanTransactionDocument, String> {
    LoanTransactionDocument getLoanTransactionDocumentByLoanTransactionId(String customerId);
}
