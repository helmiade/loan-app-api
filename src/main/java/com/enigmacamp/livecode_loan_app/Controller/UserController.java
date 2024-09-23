package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.UserService;
import com.enigmacamp.livecode_loan_app.dto.Response.AuthResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public CommonResponse<Object> getUserById(@PathVariable String id){
        AppUser app = userService.loadUserByUserId(id);
        AuthResponse authResponse = AuthResponse.builder()
                .email(app.getEmail())
                .roles(app.getRoles())
                .build();
        return CommonResponse.builder()
                .message("success")
                .data(authResponse)
                .build();

    }

}
