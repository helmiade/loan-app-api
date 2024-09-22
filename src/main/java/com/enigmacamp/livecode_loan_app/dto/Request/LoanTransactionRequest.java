package com.enigmacamp.livecode_loan_app.dto.Request;

import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransactionRequest {
    private LoanType loanTypes;
    private InstalmentType instalmentTypes;
    private Customer customers;
    private double nominal;
}

