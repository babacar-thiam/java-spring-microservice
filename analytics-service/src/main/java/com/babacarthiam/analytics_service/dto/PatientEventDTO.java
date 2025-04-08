package com.babacarthiam.analytics_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientEventDTO {

    private String patientId;
    private String patientName;
    private String patientEmail;
    private String eventType;
}
