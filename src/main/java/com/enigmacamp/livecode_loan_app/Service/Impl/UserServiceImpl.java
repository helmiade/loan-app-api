package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Mapper.AppUserMapper;
import com.enigmacamp.livecode_loan_app.Repository.UserRepository;
import com.enigmacamp.livecode_loan_app.Service.UserService;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import com.enigmacamp.livecode_loan_app.entity.Role;
import com.enigmacamp.livecode_loan_app.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public AppUser loadUserByUserId(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid Credential"));

        return AppUserMapper.mapToAppUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        return AppUserMapper.mapToAppUser(user);
    }
}
