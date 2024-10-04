package com.enigmacamp.livecode_loan_app.Repository;

import com.enigmacamp.livecode_loan_app.entity.GuaranteePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteePictureRepository extends JpaRepository<GuaranteePicture, String> {
}
