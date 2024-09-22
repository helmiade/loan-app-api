package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
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
        return CommonResponse.builder()
                .message("success create transaction")
                .data(transaction)
                .build();
    }

    @PutMapping("/{adminId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> approve(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransaction loanTransaction = loanTransactionService.approveLoanTransaction(adminId, request);
        return CommonResponse.builder()
                .message("success approve transaction")
                .data(loanTransaction)
                .build();
    }
    @GetMapping("/{id}")
    public CommonResponse<?> findById(@PathVariable String id) {
        LoanTransaction transaction = loanTransactionService.findById(id);
        return CommonResponse.builder()
                .message("success find transaction")
                .data(transaction)
                .build();
    }

}
