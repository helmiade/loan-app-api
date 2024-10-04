package com.enigmacamp.livecode_loan_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_loan_document")
public class LoanTransactionDocument {
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
    private String path;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
