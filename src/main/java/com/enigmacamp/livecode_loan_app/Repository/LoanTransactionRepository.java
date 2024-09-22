package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
}
