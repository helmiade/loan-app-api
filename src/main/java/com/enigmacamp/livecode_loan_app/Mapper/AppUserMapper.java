package com.enigmacamp.livecode_loan_app.Mapper;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import com.enigmacamp.livecode_loan_app.entity.User;

public class AppUserMapper {
    public static AppUser mapToAppUser(User user) {

        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(role -> role.getRole()).toList())
                .build();
    }
}
