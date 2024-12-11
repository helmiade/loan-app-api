package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.constant.ApprovalStatus;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
    List<LoanTransaction> findByCustomerId(String customerId);
    List<LoanTransaction> findAllByApprovalStatus(ApprovalStatus approvalStatus);
}
