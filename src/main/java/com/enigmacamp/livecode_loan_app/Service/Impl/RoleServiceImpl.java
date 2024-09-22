package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.RoleRepository;
import com.enigmacamp.livecode_loan_app.Service.RoleService;
import com.enigmacamp.livecode_loan_app.constant.ERole;
import com.enigmacamp.livecode_loan_app.entity.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> roleOptional = roleRepository.findByRole(role.getRole());

        if (roleOptional.isPresent()) {
            log.info("Role {} found in database", role.getRole());
            return roleOptional.get();
        }

        log.info("Role {} not found, saving new role", role.getRole());
        return roleRepository.save(role);
    }

}
