package com.enigmacamp.livecode_loan_app.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTypeRequest {
    @NotBlank
    private String type;
    @NotBlank
    private Long maxLoan;
}
