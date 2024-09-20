package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.InstalmentTypeRequest;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstalmentTypeService {
    public List<InstalmentType> findAll();
    public InstalmentType findById(String id);
    public InstalmentType create(InstalmentTypeRequest instalmentTypeRequest);
    public InstalmentType update(InstalmentType instalmentType);
    public void delete(String id);
}
