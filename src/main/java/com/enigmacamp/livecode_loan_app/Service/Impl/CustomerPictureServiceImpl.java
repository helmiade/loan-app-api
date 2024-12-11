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
    private final Path directoryPath= Paths.get("/home/enigma/Documents/Enigmacamp/livecode-loan-app/src/main/resources/asset/images/");

    @Override
    public CustomerPicture createFile(CustomerRequest customerRequest) {
        if (customerRequest.getProfilePicture().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        try {
            String contentType = customerRequest.getProfilePicture().getContentType();
            System.out.println(contentType);
            String fileExtension;
            if ("image/jpeg".equals(contentType)) {
                fileExtension = ".jpeg";
            } else if ("image/png".equals(contentType)) {
                fileExtension = ".png";
            }else if ("image/jpg".equals(contentType)) {
                fileExtension = ".jpg";
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file type");
            }
            Files.createDirectories(directoryPath); // Ensure directory exists
            String fileName = String.format("%d_%s", System.currentTimeMillis(), "profile_" + customerRequest.getFirstName()+fileExtension);
            Path filePath = directoryPath.resolve(fileName);

            // Save file to the directory
            Files.copy(customerRequest.getProfilePicture().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Save entity to the database
            CustomerPicture customerPicture = CustomerPicture.builder()
                    .file_name(fileName)
                    .content_type(customerRequest.getProfilePicture().getContentType())
                    .size(customerRequest.getProfilePicture().getSize())
                    .url(filePath.toString())
                    .build();

            return customerPictureRepository.saveAndFlush(customerPicture);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving file: " + e.getMessage());
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

    @Override
    public String getPathById(CustomerPicture customerId) {
        CustomerPicture customerPicture=customerPictureRepository.findById(customerId.getId()).orElse(null);
        assert customerPicture != null;
        return  customerPicture.getFile_name();
    }

}
