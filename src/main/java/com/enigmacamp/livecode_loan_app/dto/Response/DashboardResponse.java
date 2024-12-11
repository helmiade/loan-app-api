package com.enigmacamp.livecode_loan_app.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    private Integer totalLoans;
    private Integer approvedLoans;
    private Integer rejectedLoans;
    private Integer pendingLoans;
    private Double loanAmount;
    private Integer totalUsers;
    private Integer activeUsers;
    private Integer inactiveUsers;
    private Double paidLoanAmount;
    private Double unpaidLoanAmount;
}
