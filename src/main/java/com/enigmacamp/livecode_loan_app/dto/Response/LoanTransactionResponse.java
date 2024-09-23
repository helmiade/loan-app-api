package com.enigmacamp.livecode_loan_app.dto.Response;

import com.enigmacamp.livecode_loan_app.constant.ApprovalStatus;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransactionResponse {
    private String id;
    private String loanTypesId;
    private String instalmentTypesId;
    private String customerId;
    private double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus; // enum
    private List<LoanTransactionDetail> loanTransactionDetails;
    private Long createdAt;
    private Long updatedAt;
}
