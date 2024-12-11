package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Mapper.CustomerMapper;
import com.enigmacamp.livecode_loan_app.Repository.CustomerRepository;
import com.enigmacamp.livecode_loan_app.Service.CustomerPictureService;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import com.enigmacamp.livecode_loan_app.entity.User;
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
        Customer findId = findByIdOrThrowError(request.getId());

        // Handle profile picture update
        if (request.getProfilePicture() != null && !request.getProfilePicture().isEmpty()) {
            if (findId.getCustomerPicture() != null) {
                // Break the relationship before deleting picture
                CustomerPicture existingPicture = findId.getCustomerPicture();
                findId.setCustomerPicture(null);
                customerRepository.saveAndFlush(findId); // Save customer without picture

                // Delete the old picture
                customerPictureService.deleteFile("/home/enigma/Documents/Enigmacamp/livecode-loan-app/src/main/resources/asset/images/"
                        + existingPicture.getFile_name(), existingPicture);
            }
            // Create and save the new profile picture
            CustomerPicture newPicture = customerPictureService.createFile(request);
            findId.setCustomerPicture(newPicture);
        }

        // Update other customer fields
        findId.setFirstName(request.getFirstName());
        findId.setLastName(request.getLastName());
        findId.setDateOfBirth(request.getDateOfBirth());
        findId.setPhone(request.getPhone());
        findId.setStatus(request.getStatus());

        customerRepository.saveAndFlush(findId);

        return CustomerMapper.mapToCustomer(findId);
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

    @Override
    public CustomerResponse findByUser(User user) {
        Customer customer= customerRepository.findByUser(user);
        return  CustomerMapper.mapToCustomer(customer);
    }

    @Override
    public String findCustomerPictureById(String id) {
        Customer customer= findByIdOrThrowError(id);
        return customerPictureService.getPathById(customer.getCustomerPicture());
    }

    private Customer findByIdOrThrowError(String id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found"));
    }
}
