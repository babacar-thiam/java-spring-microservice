package com.babacarthiam.patient_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.babacarthiam.patient_service.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String id);

}
