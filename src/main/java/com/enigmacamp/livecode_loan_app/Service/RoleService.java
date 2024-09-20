package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role getOrSave(Role role);
}
