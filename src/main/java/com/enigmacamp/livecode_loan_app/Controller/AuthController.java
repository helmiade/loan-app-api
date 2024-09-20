package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.AuthService;
import com.enigmacamp.livecode_loan_app.dto.Request.AuthRequest;
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

    @PostMapping("/signup")
    public CommonResponse<Object> register(@RequestBody AuthRequest authRequest) {
        return authService.registerUser(authRequest);
    }

}
