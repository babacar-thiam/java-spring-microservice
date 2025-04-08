package com.babacarthiam.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babacarthiam.billing_service.entity.BillingRequestEntity;

@Repository
public interface BillingRequestRepository extends JpaRepository<BillingRequestEntity, String> {

}
