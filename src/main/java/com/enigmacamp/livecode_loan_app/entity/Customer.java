package com.enigmacamp.livecode_loan_app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Date dateOfBirth;
    @Column
    private String phone;
    @Column
    private String status;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private User user;

}
