package com.babacarthiam.analytics_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babacarthiam.analytics_service.entity.PatientEventEntity;

@Repository
public interface PatientEventRepository extends JpaRepository<PatientEventEntity, String> {

}
