package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.RegisterCustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    void deleteById(String id);
    CustomerResponse update(CustomerRequest request);
    CustomerResponse create(Customer customer);
    Customer findById(String id);
    CustomerResponse findByUser(User user);
    String findCustomerPictureById(String id);
}
