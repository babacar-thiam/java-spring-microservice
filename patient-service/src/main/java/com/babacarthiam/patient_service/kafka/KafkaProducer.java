package com.babacarthiam.patient_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.babacarthiam.patient_service.PatientEvent;
import com.babacarthiam.patient_service.entity.Patient;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event =  PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();
        
        try {
            kafkaTemplate.send("patient", event.toByteArray());
            logger.info("Sent PATIENT_CREATED event: {}", event);
        } catch (Exception e) {
            logger.error("Error sending PATIENT_CREATED event: {}", event);
        }
    }
}
