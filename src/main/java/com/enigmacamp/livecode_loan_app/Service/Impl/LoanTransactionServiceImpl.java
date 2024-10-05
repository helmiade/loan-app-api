package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTransactionRepository;
import com.enigmacamp.livecode_loan_app.Service.*;
import com.enigmacamp.livecode_loan_app.constant.ApprovalStatus;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.*;
import com.enigmacamp.livecode_loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanTransactionServiceImpl implements LoanTransactionService {
    private static final Logger log = LoggerFactory.getLogger(LoanTransactionServiceImpl.class);
    private final LoanTransactionRepository loanTransactionRepository;
    private final LoanTypeService loanTypeService;
    private final LoanTransactionDetailService loanTransactionDetailService;
    private final LoanTransactionDocumentService loanTransactionDocumentService;
    private final UserService userService;
    private final ValidationUtil validationUtil;
    @Override
    public List<LoanTransaction> findAll() {
        return loanTransactionRepository.findAll();
    }

    @Override
    public LoanTransaction createLoanTransaction(LoanTransactionRequest loanTransactionRequest) {
        validationUtil.validate(loanTransactionRequest);
        LoanType loanType = loanTypeService.findById(loanTransactionRequest.getLoanTypes().getId());
        if(loanTransactionRequest.getNominal()>loanType.getMaxLoan()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nominal loan type cannot be greater than "+loanType.getMaxLoan());
        }
        LoanTransaction loanTransaction = LoanTransaction.builder()
                .loanType(loanTransactionRequest.getLoanTypes())
                .instalmentType(loanTransactionRequest.getInstalmentTypes())
                .customer(loanTransactionRequest.getCustomers())
                .nominal(loanTransactionRequest.getNominal())
                .approvalStatus(ApprovalStatus.PENDING)
                .createdAt(System.currentTimeMillis())
                .build();

        if(loanTransactionRequest.getDocument()!=null && !loanTransactionRequest.getDocument().isEmpty()){
            loanTransactionDocumentService.createFile(loanTransactionRequest);
        }
        return loanTransactionRepository.save(loanTransaction);

    }

    @Override
    public LoanTransaction findById(String id) {
        return  findByIdOrThrowError(id);
    }

    @Override
    public LoanTransaction approveLoanTransaction(String id, ApproveTransactionRequest request) {
        LoanTransaction loanTransaction=setLoanTransaction(id, request);
        if(loanTransactionDocumentService.findByCustomer(loanTransaction.getCustomer().getId())!=null){
            loanTransaction.setApprovalStatus(ApprovalStatus.APPROVED);
            double nominal = loanTransaction.getNominal()+(loanTransaction.getNominal()*request.getInterestRate());
            request.setInterestRate(nominal);
            List<LoanTransactionDetail> loanTransactionDetail=loanTransactionDetailService.create(loanTransaction, request);
            loanTransaction.setLoanTransactionDetails(loanTransactionDetail);
        } else {
            loanTransaction.setApprovalStatus(ApprovalStatus.REJECTED);
        }

        return loanTransactionRepository.saveAndFlush(loanTransaction);
    }

    @Override
    public LoanTransaction rejectLoanTransaction(String id, ApproveTransactionRequest request) {
        LoanTransaction loanTransaction=setLoanTransaction(id, request);
        loanTransaction.setApprovalStatus(ApprovalStatus.REJECTED);
        return loanTransactionRepository.saveAndFlush(loanTransaction);
    }

    private LoanTransaction setLoanTransaction(String id, ApproveTransactionRequest request) {
        LoanTransaction loanTransaction = findByIdOrThrowError(request.getLoanTransactionId());
        if(loanTransaction.getApprovalStatus() != ApprovalStatus.PENDING){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"this transaction already approved or rejected by admin");
        }
        AppUser appUser= userService.loadUserByUserId(id);
        loanTransaction.setApprovedBy(appUser.getEmail());
        loanTransaction.setApprovedAt(System.currentTimeMillis());
        loanTransaction.setUpdatedAt(System.currentTimeMillis());
        return  loanTransaction;
    }

    @Override
    public LoanTransaction updateTransactionDetail(String id) {
        LoanTransaction loanTransaction = findByIdOrThrowError(id);
        List<LoanTransactionDetail> loanTransactionDetails= loanTransaction.getLoanTransactionDetails();
        loanTransactionDetailService.update(loanTransactionDetails);
        return loanTransaction;
    }

    private LoanTransaction findByIdOrThrowError(String id) {
        Optional<LoanTransaction> loanTransaction= loanTransactionRepository.findById(id);
        return loanTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan transaction not found"));
    }
}
