package com.enigmacamp.livecode_loan_app.Service.Impl;

import com.enigmacamp.livecode_loan_app.Repository.LoanTransactionDocumentRepository;
import com.enigmacamp.livecode_loan_app.Service.LoanTransactionDocumentService;
import com.enigmacamp.livecode_loan_app.dto.Request.LoanTransactionRequest;
import com.enigmacamp.livecode_loan_app.entity.LoanTransaction;
import com.enigmacamp.livecode_loan_app.entity.LoanTransactionDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LoanTransactionDocumentServiceImpl implements LoanTransactionDocumentService {
    private static final Logger log = LoggerFactory.getLogger(LoanTransactionDocumentServiceImpl.class);
    private final LoanTransactionDocumentRepository loanTransactionDocumentRepository;
    private final Path directoryPath= Paths.get("src/main/resources/asset/document");

    @Override
    public void createFile(LoanTransactionRequest loanTransactionRequest, LoanTransaction id) {

        if(loanTransactionRequest.getDocument().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file not found");
        try {
            Files.createDirectories(directoryPath);
            String fileName = String.format("%d %s", System.currentTimeMillis(), "document "+loanTransactionRequest.getCustomers().getId());
            Path filePath=directoryPath.resolve(fileName);
            Files.copy(loanTransactionRequest.getDocument().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            LoanTransactionDocument document = LoanTransactionDocument.builder()
                    .file_name(fileName)
                    .content_type(loanTransactionRequest.getDocument().getContentType())
                    .size(loanTransactionRequest.getDocument().getSize())
                    .path("src/main/resources/asset/images/"+fileName)
                    .customer(loanTransactionRequest.getCustomers())
                    .loanTransaction(id)
                    .build();

            loanTransactionDocumentRepository.saveAndFlush(document);


        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public LoanTransactionDocument findByTransaction(String transactionId) {
        LoanTransactionDocument loanTransactionDocument=loanTransactionDocumentRepository.getLoanTransactionDocumentByLoanTransactionId(transactionId);
        if(loanTransactionDocument==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return loanTransactionDocument;
    }

    @Override
    public void deleteFile(String path, LoanTransactionDocument loanTransactionDocument) {

    }
    private LoanTransactionDocument findByIdOrThrowError(String id) {
        Optional<LoanTransactionDocument> document= loanTransactionDocumentRepository.findById(id);
        return document.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"customer not found"));
    }
}
