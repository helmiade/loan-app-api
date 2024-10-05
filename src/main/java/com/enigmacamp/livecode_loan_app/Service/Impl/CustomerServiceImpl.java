package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Mapper.CustomerMapper;
import com.enigmacamp.livecode_loan_app.Repository.CustomerRepository;
import com.enigmacamp.livecode_loan_app.Service.CustomerPictureService;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import com.enigmacamp.livecode_loan_app.util.ValidationUtil;
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
    private final ValidationUtil validationUtil;
    private final CustomerPictureService customerPictureService;
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
    public Customer findById(String id) {
        return findByIdOrThrowError(id);
    }

    @Override
    public CustomerResponse update(CustomerRequest request) {
        validationUtil.validate(request);
        Customer findId=findByIdOrThrowError(request.getId());
        CustomerPicture customerPicture = new CustomerPicture();
        if(request.getProfilePicture()!=null) {
            if (findId.getCustomerPicture() != null) {
                CustomerPicture picture = findId.getCustomerPicture();
                findId.setCustomerPicture(null);
                customerRepository.saveAndFlush(findId);
                customerPictureService.deleteFile("src/main/resources/asset/images/" + picture.getFile_name(), picture);
            }
            customerPicture = customerPictureService.createFile(request);
        }
        Customer customer= Customer.builder()
                .id(findId.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .status(request.getStatus())
                .user(findId.getUser())
                .customerPicture(customerPicture)
                .build();
        customerRepository.saveAndFlush(customer);
        return CustomerMapper.mapToCustomer(customer);
    }

    @Override
    public CustomerResponse create(Customer customer) {
        try {
            customerRepository.saveAndFlush(customer);
            return CustomerMapper.mapToCustomer(customer);
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private Customer findByIdOrThrowError(String id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found"));
    }
}
