package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.CustomerRepository;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Request.RegisterCustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Customer customer= findByIdOrThrowError(id);
        customerRepository.delete(customer);

    }

    @Override
    public CustomerResponse update(CustomerRequest request) {

        Customer findId=findByIdOrThrowError(request.getId());
        Customer customer= Customer.builder()
                .id(findId.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .status(request.getStatus())
                .user(findId.getUser())
                .build();
        customerRepository.save(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .build();
    }

    @Override
    public CustomerResponse create(Customer customer) {
        try {
            customerRepository.saveAndFlush(customer);
            return CustomerResponse.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .dateOfBirth(customer.getDateOfBirth())
                    .phone(customer.getPhone())
                    .status(customer.getStatus())
                    .build();

        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private Customer findByIdOrThrowError(String id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found"));
    }
}
