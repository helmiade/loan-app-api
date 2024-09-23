package com.enigmacamp.livecode_loan_app.dto.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCustomerRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String status;
}
