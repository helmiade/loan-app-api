package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTypeRepository;
import com.enigmacamp.livecode_loan_app.Service.LoanTypeService;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTypeRequest;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import com.enigmacamp.livecode_loan_app.entity.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanTypeServiceImpl implements LoanTypeService {
    private final LoanTypeRepository loanTypeRepository;
    @Override
    public List<LoanType> findAll() {
        return loanTypeRepository.findAll();
    }

    @Override
    public LoanType findById(String id) {
        return findByIdOrThrowError(id);
    }

    @Override
    public LoanType create(LoanTypeRequest request) {
        LoanType loanType = LoanType.builder()
                .maxLoan(request.getMaxLoan())
                .type(request.getType())
                .build();
        return loanTypeRepository.save(loanType);
    }

    @Override
    public LoanType update(LoanType request) {
        LoanType findId= findByIdOrThrowError(request.getId());
        LoanType updatedLoanType = LoanType.builder()
                .id(findId.getId())
                .type(request.getType())
                .maxLoan(request.getMaxLoan())
                .build();

        return loanTypeRepository.save(updatedLoanType);
    }

    @Override
    public void deleteById(String id) {
        LoanType findId= findByIdOrThrowError(id);
        loanTypeRepository.delete(findId);

    }
    private LoanType findByIdOrThrowError(String id) {
        Optional<LoanType> instalmentType= loanTypeRepository.findById(id);
        return instalmentType.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Loan type not found"));
    }
}
