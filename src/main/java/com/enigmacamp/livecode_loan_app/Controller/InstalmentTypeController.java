package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.InstalmentTypeService;
import com.enigmacamp.livecode_loan_app.dto.Request.InstalmentTypeRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.entity.InstalmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instalment-types")
public class InstalmentTypeController {
    private final InstalmentTypeService instalmentTypeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResponse<?> create(@RequestBody InstalmentTypeRequest instalmentTypeRequest) {
        InstalmentType instalmentType1= instalmentTypeService.create(instalmentTypeRequest);
        return CommonResponse.builder()
                .message("Instalment Type Created")
                .data(instalmentType1)
                .build();
    }

    @GetMapping
    public CommonResponse<?> getAll() {
        List<InstalmentType> instalmentTypeList = instalmentTypeService.findAll();
        return CommonResponse.builder()
                .message("Find all instalment type")
                .data(instalmentTypeList)
                .build();
    }

    @GetMapping("/{id}")
    public CommonResponse<?> getById(@PathVariable String id) {
        InstalmentType instalmentType = instalmentTypeService.findById(id);
        return  CommonResponse.builder()
                .message("Find instalment type")
                .data(instalmentType)
                .build();
    }

    @PutMapping
    public CommonResponse<?> update(@RequestBody InstalmentType instalmentType) {
        InstalmentType instalmentType1=instalmentTypeService.update(instalmentType);
        return  CommonResponse.builder()
                .message("Instalment Type Updated")
                .data(instalmentType1)
                .build();

    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        instalmentTypeService.delete(id);
    }
}
