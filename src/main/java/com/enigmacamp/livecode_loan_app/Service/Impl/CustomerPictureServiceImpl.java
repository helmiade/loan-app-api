package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.CustomerPictureRepository;
import com.enigmacamp.livecode_loan_app.Service.CustomerPictureService;
import com.enigmacamp.livecode_loan_app.Service.CustomerService;
import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.entity.Customer;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class CustomerPictureServiceImpl implements CustomerPictureService {
    private final CustomerPictureRepository customerPictureRepository;
    private final Path directoryPath= Paths.get("src/main/resources/asset/images");

    @Override
    public CustomerPicture createFile(CustomerRequest customerRequest) {
        if(customerRequest.getProfilePicture().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        try {
            Files.createDirectories(directoryPath);
            String fileName = String.format("%d %s", System.currentTimeMillis(), customerRequest.getProfilePicture().getOriginalFilename());
            Path filePath=directoryPath.resolve(fileName);
            Files.copy(customerRequest.getProfilePicture().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            CustomerPicture customerPicture = CustomerPicture.builder()
                    .file_name(fileName)
                    .content_type(customerRequest.getProfilePicture().getContentType())
                    .size(customerRequest.getProfilePicture().getSize())
                    .url("/api/customer/"+customerRequest.getId()+"/image")
                    .build();

            return customerPictureRepository.saveAndFlush(customerPicture);


        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource findByPath(String path) {
        Path filePath=Paths.get(path);
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @Override
    public void deleteFile(String path, CustomerPicture customerPicture) {
        Path filePath=Paths.get(path);
        if(!Files.exists(filePath)) {
            customerPictureRepository.deleteById(customerPicture.getId());
        }
        try {
            Files.delete(filePath);
            customerPictureRepository.delete(customerPicture);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}
