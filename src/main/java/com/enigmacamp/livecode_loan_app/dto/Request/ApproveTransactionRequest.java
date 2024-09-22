package com.enigmacamp.livecode_loan_app.dto.Request;

import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveTransactionRequest {
    private String loanTransactionId;
    private Double interestRate;
}
