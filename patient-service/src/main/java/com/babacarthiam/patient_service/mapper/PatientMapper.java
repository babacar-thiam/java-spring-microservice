package com.babacarthiam.patient_service.mapper;

import java.time.LocalDate;
import com.babacarthiam.patient_service.dto.PatientRequestDTO;
import com.babacarthiam.patient_service.dto.PatientResponseDTO;
import com.babacarthiam.patient_service.dto.PatientUpdateDTO;
import com.babacarthiam.patient_service.entity.Patient;

public class PatientMapper {

    public static PatientResponseDTO toPatientResponseDTO(Patient patient) {
       
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        patientResponseDTO.setRegisteredDate(patient.getRegisteredDate().toString());

        return patientResponseDTO;
    }

    public static Patient toPatient(PatientRequestDTO patientRequestDTO) {
        
        Patient patient = new Patient();

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        return patient;
    }

    public static void updatePatientFromDTO(Patient patient, PatientUpdateDTO patientUpdateDTO) {
        
        patient.setName(patientUpdateDTO.getName());
        patient.setEmail(patientUpdateDTO.getEmail());
        patient.setAddress(patientUpdateDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientUpdateDTO.getDateOfBirth()));
    }
}
