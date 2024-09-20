package com.enigmacamp.livecode_loan_app.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
    private String token;
}
