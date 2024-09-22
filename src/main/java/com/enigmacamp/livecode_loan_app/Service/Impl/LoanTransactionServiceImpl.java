package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTransactionRepository;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDetailService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.Service.UserService;
import com.enigmacamp.livecode_loan_app.constant.ApprovalStatus;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.livecode_loan_app.entity.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final UserService userService;
    @Override
    public List<LoanTransaction> findAll() {
        return loanTransactionRepository.findAll();
    }

    @Override
    public LoanTransaction createLoanTransaction(LoanTransactionRequest loanTransactionRequest) {
        LoanTransaction loanTransaction = LoanTransaction.builder()
                .loanType(loanTransactionRequest.getLoanTypes())
                .instalmentType(loanTransactionRequest.getInstalmentTypes())
                .customer(loanTransactionRequest.getCustomers())
                .nominal(loanTransactionRequest.getNominal())
                .createdAt(System.currentTimeMillis())
                .build();
        return loanTransactionRepository.save(loanTransaction);
    }

    @Override
    public LoanTransaction findById(String id) {
        return  findByIdOrThrowError(id);
    }

    @Override
    public LoanTransaction approveLoanTransaction(String id, ApproveTransactionRequest request) {
        LoanTransaction loanTransaction = findByIdOrThrowError(request.getLoanTransactionId());
        AppUser appUser= userService.loadUserByUserId(id);
        String adminEmail= SecurityContextHolder.getContext().getAuthentication().getName();
        loanTransaction.setApprovedBy(appUser.getEmail());
        loanTransaction.setApprovedAt(System.currentTimeMillis());
        loanTransaction.setApprovalStatus(ApprovalStatus.APPROVED);
        loanTransaction.setUpdatedAt(System.currentTimeMillis());

        double nominal = loanTransaction.getNominal()+(loanTransaction.getNominal()*request.getInterestRate());
        request.setInterestRate(nominal);
        List<LoanTransactionDetail> loanTransactionDetail=loanTransactionDetailService.create(loanTransaction, request);
        loanTransaction.setLoanTransactionDetails(loanTransactionDetail);

        return loanTransactionRepository.save(loanTransaction);
    }

    private LoanTransaction findByIdOrThrowError(String id) {
        Optional<LoanTransaction> loanTransaction= loanTransactionRepository.findById(id);
        return loanTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan transaction not found"));
    }
}
