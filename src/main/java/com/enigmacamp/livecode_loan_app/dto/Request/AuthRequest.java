package com.enigmacamp.livecode_loan_app.dto.Request;

import com.enigmacamp.livecode_loan_app.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 6)
    private String password;
}
