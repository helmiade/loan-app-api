package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
}
