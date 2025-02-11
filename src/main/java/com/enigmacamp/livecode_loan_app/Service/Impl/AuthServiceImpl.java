package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Mapper.AppUserMapper;
import com.enigmacamp.livecode_loan_app.Repository.UserRepository;
import com.enigmacamp.livecode_loan_app.Service.AuthService;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.Service.RoleService;
import com.enigmacamp.livecode_loan_app.constant.ERole;
import com.enigmacamp.livecode_loan_app.dto.Request.AuthRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.RegisterCustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.AuthResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.LoginResponse;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.Role;
import com.enigmacamp.livecode_loan_app.entity.User;
import com.enigmacamp.livecode_loan_app.security.JwtUtil;
import com.enigmacamp.livecode_loan_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResponse<Object>  registerUser(AuthRequest authRequest) {
        validationUtil.validate(authRequest);
        try {

            Role roleAdmin = roleService.getOrSave(Role.builder().role(ERole.ROLE_ADMIN).build());
            Role roleStaf = roleService.getOrSave(Role.builder().role(ERole.ROLE_STAFF).build());
            List<Role> roles = Arrays.asList(roleAdmin, roleStaf);


            User user = User.builder()
                    .email(authRequest.getEmail())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .roles(roles) // Convert to List
                    .build();

            userRepository.save(user);
            AuthResponse response = AuthResponse.builder()
                    .email(user.getEmail())
                    .roles(user.getRoles().stream().map(role -> role.getRole()).toList())
                    .build();

            return CommonResponse.builder()
                    .message("success register")
                    .data(response)
                    .build();

        } catch (DataIntegrityViolationException e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");}
    }

    @Override
    public LoginResponse<Object> loginUser(AuthRequest authRequest) {
        validationUtil.validate(authRequest);
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            AppUser appUser = (AppUser) authenticate.getPrincipal();
            String token = jwtUtil.generateToken(appUser);

            AuthResponse response = AuthResponse.builder()
                    .email(appUser.getEmail())
                    .roles(appUser.getRoles())
                    .build();

            return (LoginResponse<Object>) LoginResponse.builder()
                    .message("success login")
                    .data(response)
                    .token(token)
                    .build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResponse<Object>  registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        validationUtil.validate(registerCustomerRequest);
        try {

            Role roleCustomer = roleService.getOrSave(Role.builder().role(ERole.ROLE_CUSTOMER).build());
            List<Role> roles = new ArrayList<>();
            roles.add(roleCustomer);

            User user = User.builder()
                    .email(registerCustomerRequest.getEmail())
                    .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                    .roles(roles) // Convert to List
                    .build();

            userRepository.saveAndFlush(user);

            Customer customer= Customer.builder()
                    .phone(registerCustomerRequest.getPhone())
                    .dateOfBirth(registerCustomerRequest.getDateOfBirth())
                    .firstName(registerCustomerRequest.getFirstName())
                    .lastName(registerCustomerRequest.getLastName())
                    .status(registerCustomerRequest.getStatus())
                    .user(user)
                    .build();
            customerService.create(customer);
            AuthResponse response = AuthResponse.builder()
                    .email(user.getEmail())
                    .roles(user.getRoles().stream().map(role -> role.getRole()).toList())
                    .build();

            return CommonResponse.builder()
                    .message("success register")
                    .data(response)
                    .build();

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");}
    }

}
