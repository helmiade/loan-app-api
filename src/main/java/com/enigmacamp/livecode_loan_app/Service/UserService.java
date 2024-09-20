package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);

}
