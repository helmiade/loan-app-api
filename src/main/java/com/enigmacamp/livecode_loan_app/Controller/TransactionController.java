package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.LoanTransactionResponse;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transactions")
public class TransactionController {
    private final LoanTransactionService loanTransactionService;

    @GetMapping
    public CommonResponse<?> findAll() {
        List<LoanTransaction> transactions = loanTransactionService.findAll();
        return CommonResponse.builder()
                .message("success find all transactions")
                .data(transactions)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public CommonResponse<?> create(@RequestBody LoanTransactionRequest request) {
        LoanTransaction transaction = loanTransactionService.createLoanTransaction(request);
        LoanTransactionResponse response= LoanTransactionResponse.builder()
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
        return CommonResponse.builder()
                .message("success create transaction")
                .data(response)
                .build();
    }

    @PutMapping("/{adminId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> approve(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransaction transaction = loanTransactionService.approveLoanTransaction(adminId, request);
        LoanTransactionResponse response= LoanTransactionResponse.builder()
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
        return CommonResponse.builder()
                .message("success approve transaction")
                .data(response)
                .build();
    }
    @GetMapping("/{id}")
    public CommonResponse<?> findById(@PathVariable String id) {
        LoanTransaction transaction = loanTransactionService.findById(id);
        LoanTransactionResponse response= LoanTransactionResponse.builder()
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
        return CommonResponse.builder()
                .message("success find transaction")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/pay")
    public CommonResponse<?> pay(@PathVariable String id) {
        LoanTransaction transaction= loanTransactionService.updateTransactionDetail(id);
        LoanTransactionResponse response= LoanTransactionResponse.builder()
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
        return CommonResponse.builder()
                .message("success pay transaction")
                .data(response)
                .build();
    }


}
