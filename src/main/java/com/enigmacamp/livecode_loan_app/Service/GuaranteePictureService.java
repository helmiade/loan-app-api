package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.GuaranteePicture;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;

public interface GuaranteePictureService {
    GuaranteePicture createFile(LoanTransactionDocument loanTransactionDocument);

}
