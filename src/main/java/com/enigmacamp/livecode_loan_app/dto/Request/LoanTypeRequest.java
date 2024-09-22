package com.enigmacamp.livecode_loan_app.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTypeRequest {
    private String type;
    private Long maxLoan;
}
