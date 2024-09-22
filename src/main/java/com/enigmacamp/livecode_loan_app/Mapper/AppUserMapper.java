package com.enigmacamp.livecode_loan_app.Mapper;

import com.enigmacamp.livecode_loan_app.constant.ERole;
import com.enigmacamp.livecode_loan_app.entity.AppUser;
import com.enigmacamp.livecode_loan_app.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AppUserMapper {
    public static AppUser mapToAppUser(User user) {

//        List<ERole> eroles = new ArrayList<>();
//        user.getRoles().forEach(role -> {eroles.add(role.getRole());});
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(role -> role.getRole()).toList())
                .build();
    }
}
