package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTransactionDetailRepository;
import com.enigmacamp.livecode_loan_app.Service.GuaranteePictureService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDetailService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDocumentService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.constant.EInstalmentType;
import com.enigmacamp.livecode_loan_app.constant.LoanStatus;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.GuaranteePicture;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;
    private final LoanTransactionDocumentService loanTransactionDocumentService;
    private final GuaranteePictureService guaranteePictureService;
//    private final LoanTransactionService loanTransactionService;

    @Override
    public List<LoanTransactionDetail> create(LoanTransaction loanTransaction,ApproveTransactionRequest request) {
        EInstalmentType instalmentType = loanTransaction.getInstalmentType().getInstalmentType();
        List<LoanTransactionDetail> loanTransactionDetails = new ArrayList<>();
        LoanTransactionDocument loanTransactionDocument= loanTransactionDocumentService.findByTransaction(loanTransaction.getId());
        GuaranteePicture guaranteePicture=guaranteePictureService.createFile(loanTransactionDocument);
        double numberOfMonths = 0;
        switch (instalmentType) {
            case ONE_MONTH:
                numberOfMonths = 1.0;
                break;
            case THREE_MONTHS:
                numberOfMonths = 3.0;
                break;
            case SIXTH_MONTHS:
                numberOfMonths = 6.0;
                break;
            case NINE_MONTHS:
                numberOfMonths = 9.0;
                break;
            case TWELVE_MONTHS:
                numberOfMonths = 12.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid instalment type: " + instalmentType);
        }

        for (int i = 1; i <= numberOfMonths; i++) {
            LoanTransactionDetail loanTransactionDetail = LoanTransactionDetail.builder()
                    .loanTransaction(loanTransaction)
                    .nominal(request.getInterestRate() / numberOfMonths)
                    .loanStatus(LoanStatus.UNPAID)
                    .createdAt(System.currentTimeMillis())
                    .guaranteePicture(guaranteePicture)
                    .build();

            loanTransactionDetailRepository.saveAndFlush(loanTransactionDetail);
            loanTransactionDetails.add(loanTransactionDetail);
        }

        return loanTransactionDetails;
    }

    @Override
    public void update(List<LoanTransactionDetail> loanTransactionDetail) {
        List<LoanTransactionDetail> loanTransactionDetails = loanTransactionDetailRepository.findByLoanStatus(LoanStatus.UNPAID);
        if(loanTransactionDetails.isEmpty()){
            return;
        }
        LoanTransactionDetail loanTransactionDetailToUpdate = loanTransactionDetails.get(0);
        loanTransactionDetailToUpdate.setLoanStatus(LoanStatus.PAID);
        loanTransactionDetailToUpdate.setTransactionDate(System.currentTimeMillis());
        loanTransactionDetailToUpdate.setUpdatedAt(System.currentTimeMillis());
        loanTransactionDetailRepository.saveAndFlush(loanTransactionDetailToUpdate);
    }

    @Override
    public void payById(String id) {
        Optional<LoanTransactionDetail> loanTransactionDetail=loanTransactionDetailRepository.findById(id);
        if (loanTransactionDetail.isPresent()) {
            LoanTransactionDetail loanTransactionDetailToUpdate = loanTransactionDetail.get();
            loanTransactionDetailToUpdate.setLoanStatus(LoanStatus.PAID);
            loanTransactionDetailToUpdate.setUpdatedAt(System.currentTimeMillis());
            loanTransactionDetailRepository.saveAndFlush(loanTransactionDetailToUpdate);
        }
    }
}
