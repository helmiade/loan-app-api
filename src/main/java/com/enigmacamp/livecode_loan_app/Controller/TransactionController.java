package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Mapper.LoanTransactionMapper;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.LoanTransactionResponse;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public CommonResponse<?> create(@RequestPart("request") LoanTransactionRequest request,
                                    @RequestPart(value = "document", required = false) MultipartFile file) {
        if(file != null && !file.isEmpty()) {
            request.setDocument(file);
        }
        LoanTransaction transaction = loanTransactionService.createLoanTransaction(request);
        LoanTransactionResponse response= LoanTransactionMapper.mapToLoanTransactionResponse(transaction);
        return CommonResponse.builder()
                .message("success create transaction")
                .data(response)
                .build();
    }

    @PutMapping("/{adminId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> approve(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransaction transaction = loanTransactionService.approveLoanTransaction(adminId, request);
        LoanTransactionResponse response= LoanTransactionMapper.mapToLoanTransactionResponse(transaction);
        return CommonResponse.builder()
                .message("success approve transaction")
                .data(response)
                .build();
    }
    @PutMapping("/{adminId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> reject(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransaction transaction = loanTransactionService.rejectLoanTransaction(adminId, request);
        LoanTransactionResponse response= LoanTransactionMapper.mapToLoanTransactionResponse(transaction);
        return CommonResponse.builder()
                .message("success reject transaction")
                .data(response)
                .build();
    }
    @GetMapping("/{id}")
    public CommonResponse<?> findById(@PathVariable String id) {
        LoanTransaction transaction = loanTransactionService.findById(id);
        LoanTransactionResponse response= LoanTransactionMapper.mapToLoanTransactionResponse(transaction);
        return CommonResponse.builder()
                .message("success find transaction")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/pay")
    public CommonResponse<?> pay(@PathVariable String id) {
        LoanTransaction transaction= loanTransactionService.updateTransactionDetail(id);
        LoanTransactionResponse response= LoanTransactionMapper.mapToLoanTransactionResponse(transaction);
        return CommonResponse.builder()
                .message("success pay transaction")
                .data(response)
                .build();
    }


}
