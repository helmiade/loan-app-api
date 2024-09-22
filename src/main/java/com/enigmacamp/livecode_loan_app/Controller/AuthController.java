package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.AuthService;
import com.enigmacamp.livecode_loan_app.dto.Request.AuthRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.RegisterCustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signin")
    public LoginResponse<Object> login(@RequestBody AuthRequest authRequest) {
        return authService.loginUser(authRequest);
    }

    @PostMapping("/signup/admin")
    public CommonResponse<Object> register(@RequestBody AuthRequest authRequest) {
        return authService.registerUser(authRequest);
    }

    @PostMapping("/signup/customer")
    public CommonResponse<Object> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        return authService.registerCustomer(request);
    }

}
