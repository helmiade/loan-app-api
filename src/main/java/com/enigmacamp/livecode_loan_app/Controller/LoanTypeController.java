package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.LoanTypeService;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTypeRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.entity.LoanType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-types")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    @GetMapping
    public CommonResponse<?> findAll() {
        List<LoanType> loanTypes= loanTypeService.findAll();
        return CommonResponse.builder()
                .message("success get all loan types")
                .data(loanTypes)
                .build();
    }

    @GetMapping("/{id}")
    public CommonResponse<?> findById(@PathVariable String id) {
        LoanType loanType = loanTypeService.findById(id);
        return  CommonResponse.builder()
                .message("success get loan type")
                .data(loanType)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> create(@RequestBody LoanTypeRequest request) {
        LoanType loanType = loanTypeService.create(request);
        return CommonResponse.builder()
                .message("success create loan type")
                .data(loanType)
                .build();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> update(@RequestBody LoanType request) {
        LoanType loanType = loanTypeService.update(request);
        return CommonResponse.builder()
                .message("success update loan type")
                .data(loanType)
                .build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        loanTypeService.deleteById(id);
    }
}
