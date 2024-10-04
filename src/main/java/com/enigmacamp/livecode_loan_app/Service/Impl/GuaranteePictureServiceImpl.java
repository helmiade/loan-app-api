package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.GuaranteePictureRepository;
import com.enigmacamp.livecode_loan_app.Service.GuaranteePictureService;
import com.enigmacamp.livecode_loan_app.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class GuaranteePictureServiceImpl implements GuaranteePictureService {
    private final GuaranteePictureRepository guaranteePictureRepository;
    private final Path directoryPath= Paths.get("src/main/resources/asset/guarantee");

    @Override
    public GuaranteePicture createFile(LoanTransactionDocument loanTransactionDocument) {
        GuaranteePicture guaranteePicture = GuaranteePicture.builder()
                .path(loanTransactionDocument.getPath())
                .size(loanTransactionDocument.getSize())
                .content_type(loanTransactionDocument.getContent_type())
                .file_name(loanTransactionDocument.getFile_name())
                .build();

        return guaranteePictureRepository.saveAndFlush(guaranteePicture);
    }
}
