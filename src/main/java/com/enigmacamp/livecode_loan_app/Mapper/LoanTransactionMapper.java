package com.enigmacamp.livecode_loan_app.Mapper;

import com.enigmacamp.livecode_loan_app.constant.ApprovalStatus;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.LoanTransactionResponse;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;

public class LoanTransactionMapper {
    public static LoanTransactionResponse mapToLoanTransactionResponse(LoanTransaction transaction) {
        return LoanTransactionResponse.builder()
                .id(transaction.getId())
                .loanTypesId(transaction.getLoanType().getId())
                .instalmentTypesId(transaction.getInstalmentType().getId())
                .customerId(transaction.getCustomer().getId())
                .nominal(transaction.getNominal())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .approvalStatus(transaction.getApprovalStatus())
                .approvedAt(transaction.getApprovedAt())
                .approvedBy(transaction.getApprovedBy())
                .loanTransactionDetails(transaction.getLoanTransactionDetails())
                .build();
    }
    public static LoanTransaction mapToLoanTransaction(LoanTransactionRequest loanTransactionRequest) {
        return LoanTransaction.builder()
                .loanType(loanTransactionRequest.getLoanTypes())
                .instalmentType(loanTransactionRequest.getInstalmentTypes())
                .customer(loanTransactionRequest.getCustomers())
                .nominal(loanTransactionRequest.getNominal())
                .approvalStatus(ApprovalStatus.PENDING)
                .createdAt(System.currentTimeMillis())
                .build();
    }
}
