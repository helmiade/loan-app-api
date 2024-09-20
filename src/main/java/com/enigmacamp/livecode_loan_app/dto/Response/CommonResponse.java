package com.enigmacamp.livecode_loan_app.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
