package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.constant.LoanStatus;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanTransactionDetailRepository extends JpaRepository<LoanTransactionDetail, String> {
    List<LoanTransactionDetail> findByLoanStatus(LoanStatus loanStatus);
}
