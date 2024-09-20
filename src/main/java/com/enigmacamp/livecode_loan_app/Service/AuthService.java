package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.AuthRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.AuthResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.LoginResponse;

public interface AuthService {
    CommonResponse<Object> registerUser(AuthRequest authRequest);
    LoginResponse<Object> loginUser(AuthRequest authRequest);


}
