package com.enigmacamp.livecode_loan_app.dto.Request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionRequest {
    // Getters dan Setters
    private String orderId;
    private BigDecimal grossAmount;

}

