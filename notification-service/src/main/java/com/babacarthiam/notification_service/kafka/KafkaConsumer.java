package com.babacarthiam.notification_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.babacarthiam.notification_service.PatientEvent;
import com.google.protobuf.InvalidProtocolBufferException;

@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "notification-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            logger.info("Patient ID: {}, Name: {}, Email: {}, Event Type: {}",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail(), 
                    patientEvent.getEventType()); 
    
        } catch (InvalidProtocolBufferException e) {
            logger.error("Failed to parse event: {}", e.getMessage());
        }
    }

}
