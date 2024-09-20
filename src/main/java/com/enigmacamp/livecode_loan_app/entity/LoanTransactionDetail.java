package com.enigmacamp.livecode_loan_app.entity;

import com.enigmacamp.livecode_loan_app.constant.LoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "t_loan_trx_detail")
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "trx_date")
    private Long transactionDate;
    @Column
    private Double nominal;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private LoanTransaction loanTransaction;
    @Column(name = "loan_status")
    private LoanStatus loanStatus; // enum
    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "update_at")
    private Long updatedAt;
}
