package com.babacarthiam.analytics_service.service;

import org.springframework.stereotype.Service;

import com.babacarthiam.analytics_service.dto.PatientEventDTO;
import com.babacarthiam.analytics_service.entity.PatientEventEntity;
import com.babacarthiam.analytics_service.repository.PatientEventRepository;

@Service
public class PatientEventTypeService {

    private final PatientEventRepository patientEventRepository;

    public PatientEventTypeService(PatientEventRepository patientEventRepository) {
        this.patientEventRepository = patientEventRepository;
    }

    public void savePatientEventType(PatientEventDTO patientEventDTO) {
        PatientEventEntity patientEventEntity = new PatientEventEntity();

        patientEventEntity.setPatientId(patientEventDTO.getPatientId());
        patientEventEntity.setPatientName(patientEventDTO.getPatientName());
        patientEventEntity.setPatientEmail(patientEventDTO.getPatientEmail());
        patientEventEntity.setEventType(patientEventDTO.getEventType());
        
        patientEventRepository.save(patientEventEntity);
    }

}
