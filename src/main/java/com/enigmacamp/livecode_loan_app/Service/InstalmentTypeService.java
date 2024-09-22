package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.InstalmentTypeRequest;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstalmentTypeService {
    List<InstalmentType> findAll();
    InstalmentType findById(String id);
    InstalmentType create(InstalmentTypeRequest instalmentTypeRequest);
    InstalmentType update(InstalmentType instalmentType);
    void delete(String id);
}
