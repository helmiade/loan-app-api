package com.enigmacamp.livecode_loan_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_customer_picture")
@Builder
public class CustomerPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String content_type;
    @Column
    private String file_name;
    @Column
    private Long size;
    @Column
    private String url;
}
