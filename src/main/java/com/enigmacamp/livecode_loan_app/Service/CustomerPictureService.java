package com.enigmacamp.livecode_loan_app.Service;

import com.enigmacamp.livecode_loan_app.dto.Request.CustomerRequest;
import com.enigmacamp.livecode_loan_app.entity.CustomerPicture;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CustomerPictureService {
    CustomerPicture createFile(CustomerRequest customerRequest);
    Resource findByPath(String path);
    void deleteFile(String path, CustomerPicture customerPicture);
}
