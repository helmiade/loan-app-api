package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public CommonResponse<Object> findAll() {
        List<Customer> customers = customerService.findAll();

        return CommonResponse.builder()
                .message("success find all")
                .data(customers)
                .build();
    }

    @PutMapping
    public CommonResponse<Object> update(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse response= customerService.update(customerRequest);
        return CommonResponse.builder()
                .message("success update customer")
                .data(response)
                .build();    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        customerService.deleteById(id);
    }
}
