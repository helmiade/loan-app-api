package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTransactionDetailRepository;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDetailService;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionService;
import com.enigmacamp.livecode_loan_app.constant.EInstalmentType;
import com.enigmacamp.livecode_loan_app.constant.LoanStatus;
import com.enigmacamp.livecode_loan_app.dto.Request.ApproveTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoanTransactionDetailServiceImpl implements LoanTransactionDetailService {
    private final LoanTransactionDetailRepository loanTransactionDetailRepository;
//    private final LoanTransactionService loanTransactionService;

    @Override
    public List<LoanTransactionDetail> create(LoanTransaction loanTransaction,ApproveTransactionRequest request) {
//        LoanTransaction loanTransaction = loanTransactionService.findById(request.getLoanTransactionId());
        EInstalmentType instalmentType = loanTransaction.getInstalmentType().getInstalmentType();
        List<LoanTransactionDetail> loanTransactionDetails = new ArrayList<>();

        int numberOfMonths = 0;
        switch (instalmentType) {
            case ONE_MONTH:
                numberOfMonths = 1;
                break;
            case THREE_MONTHS:
                numberOfMonths = 3;
                break;
            case SIXTH_MONTHS:
                numberOfMonths = 6;
                break;
            case NINE_MONTHS:
                numberOfMonths = 9;
                break;
            case TWELVE_MONTHS:
                numberOfMonths = 12;
                break;
            default:
                throw new IllegalArgumentException("Invalid instalment type: " + instalmentType);
        }

        for (int i = 1; i <= numberOfMonths; i++) {
            LoanTransactionDetail loanTransactionDetail = LoanTransactionDetail.builder()
                    .loanTransaction(loanTransaction)
                    .nominal(request.getInterestRate()/numberOfMonths)
                    .loanStatus(LoanStatus.UNPAID)
                    .createdAt(System.currentTimeMillis())
                    .build();

            loanTransactionDetailRepository.saveAndFlush(loanTransactionDetail);
            loanTransactionDetails.add(loanTransactionDetail);
        }

        return loanTransactionDetails;
    }


}
