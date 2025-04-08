package com.babacarthiam.analytics_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.babacarthiam.analytics_service.PatientEvent;
import com.babacarthiam.analytics_service.dto.PatientEventDTO;
import com.babacarthiam.analytics_service.service.PatientEventTypeService;
import com.google.protobuf.InvalidProtocolBufferException;

@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final PatientEventTypeService patientEventTypeService;

    public KafkaConsumer(PatientEventTypeService patientEventTypeService) {
        this.patientEventTypeService = patientEventTypeService;
    }

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            logger.info("Patient ID: {}, Name: {}, Email: {},  Event Type: {}", patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail(), patientEvent.getEventType());

            PatientEventDTO patientEventDTO = new PatientEventDTO();
            patientEventDTO.setPatientId(patientEvent.getPatientId());
            patientEventDTO.setPatientName(patientEvent.getName());
            patientEventDTO.setPatientEmail(patientEvent.getEmail());
            patientEventDTO.setEventType(patientEvent.getEventType());
            patientEventTypeService.savePatientEventType(patientEventDTO);
            logger.info("Patient event saved successfully");

        } catch (InvalidProtocolBufferException e) {
            logger.error("Failed to parse event: {}", e.getMessage());
        }
    }

}
