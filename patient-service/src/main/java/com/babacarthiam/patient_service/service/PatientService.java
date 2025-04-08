package com.babacarthiam.patient_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.babacarthiam.patient_service.dto.PatientRequestDTO;
import com.babacarthiam.patient_service.dto.PatientResponseDTO;
import com.babacarthiam.patient_service.dto.PatientUpdateDTO;
import com.babacarthiam.patient_service.entity.Patient;
import com.babacarthiam.patient_service.exception.EmailAlreadyExistsException;
import com.babacarthiam.patient_service.exception.PatientNotFoundException;
import com.babacarthiam.patient_service.grpc.BillingServiceGrpcClient;
import com.babacarthiam.patient_service.kafka.KafkaProducer;
import com.babacarthiam.patient_service.mapper.PatientMapper;
import com.babacarthiam.patient_service.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, 
            KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toPatientResponseDTO(newPatient);
    }

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toPatientResponseDTO)
                .toList();
    }

    public PatientResponseDTO getPatientById(String id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
        return PatientMapper.toPatientResponseDTO(patient);
    }

    public PatientResponseDTO updatePatient(String id, PatientUpdateDTO patientUpdateDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientUpdateDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientUpdateDTO.getEmail());
        }

        PatientMapper.updatePatientFromDTO(patient, patientUpdateDTO);
        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toPatientResponseDTO(updatedPatient);
    }

    public void deletePatient(String id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
        patientRepository.delete(patient);
    }
}
