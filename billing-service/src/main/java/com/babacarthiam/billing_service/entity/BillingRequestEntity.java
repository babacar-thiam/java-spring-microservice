package com.babacarthiam.billing_service.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "billing_requests")
public class BillingRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String patientId;
    private String patientName;
    private String patientEmail;
    private String status;

    @CreationTimestamp
    private LocalDate createdAt;

}
