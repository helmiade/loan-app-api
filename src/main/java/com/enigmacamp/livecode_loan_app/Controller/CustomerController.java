package com.enigmacamp.livecode_loan_app.Controller;

import com.enigmacamp.livecode_loan_app.Service.CustomerPictureService;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.dto.Response.CommonResponse;
import com.enigmacamp.livecode_loan_app.dto.Response.CustomerResponse;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import com.enigmacamp.livecode_loan_app.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerService customerService;
    private final CustomerPictureService customerPictureService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public CommonResponse<Object> findAll() {
        List<Customer> customers = customerService.findAll();

        return CommonResponse.builder()
                .message("success find all")
                .data(customers)
                .build();
    }

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public CommonResponse<Object> update(@RequestPart("customer") CustomerRequest customerRequest,
                                         @RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture) {
        if (profilePicture != null && !profilePicture.isEmpty()) {
            customerRequest.setProfilePicture(profilePicture);
        }
        CustomerResponse response= customerService.update(customerRequest);
        return CommonResponse.builder()
                .message("success update customer")
                .data(response)
                .build();    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        customerService.deleteById(id);
    }

    @GetMapping("/user/{user}")
    @PreAuthorize("hasRole('CUSTOMER')")
    @CrossOrigin(origins = "http://localhost:5173")
    public CommonResponse<Object> findByUser(@PathVariable User user) {
        CustomerResponse customer = customerService.findByUser(user);
        return CommonResponse.builder()
                .message("success find customer")
                .data(customer)
                .build();
    }

    @DeleteMapping("/{id}/image")
    @PreAuthorize("hasRole('CUSTOMER')")
    public void deleteImage(@PathVariable String id) {
        Customer customer = customerService.findById(id);
        CustomerPicture customerPicture= customer.getCustomerPicture();
        customer.setCustomerPicture(null);
        customerService.create(customer);
        customerPictureService.deleteFile("src/main/resources/asset/images/"+customerPicture.getFile_name(),customerPicture);
    }

    @GetMapping("/picture/{id}")
    public CommonResponse<Object> findPicture(@PathVariable String id) {
        String path= customerService.findCustomerPictureById(id);
        return CommonResponse.builder()
                .message("success find picture")
                .data(path)
                .build();
    }

}
