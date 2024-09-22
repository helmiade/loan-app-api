package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.InstalmentTypeRepository;
import com.enigmacamp.livecode_loan_app.Service.InstalmentTypeService;
import com.enigmacamp.livecode_loan_app.dto.Request.InstalmentTypeRequest;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstalmentTypeServiceImpl implements InstalmentTypeService {
    private final InstalmentTypeRepository instalmentTypeRepository;
    @Override
    public List<InstalmentType> findAll() {
        return instalmentTypeRepository.findAll();
    }

    @Override
    public InstalmentType findById(String id) {
        return  findByIdOrThrowError(id);
    }

    @Override
    public InstalmentType create(InstalmentTypeRequest instalmentTypeRequest) {
        InstalmentType instalmentType= InstalmentType.builder()
                .instalmentType(instalmentTypeRequest.getInstalmentType())
                .build();
        return instalmentTypeRepository.save(instalmentType);
    }

    @Override
    public InstalmentType update(InstalmentType instalmentType) {
        InstalmentType instalmentTypeUpdated = findByIdOrThrowError(instalmentType.getId());
        instalmentTypeUpdated.setInstalmentType(instalmentType.getInstalmentType());
        return instalmentTypeRepository.save(instalmentTypeUpdated);

    }

    @Override
    public void delete(String id) {
        InstalmentType instalmentType = findByIdOrThrowError(id);
        instalmentTypeRepository.delete(instalmentType);
    }

    private InstalmentType findByIdOrThrowError(String id) {
        Optional<InstalmentType> instalmentType= instalmentTypeRepository.findById(id);
        return instalmentType.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"instalment type not found"));
    }
}
