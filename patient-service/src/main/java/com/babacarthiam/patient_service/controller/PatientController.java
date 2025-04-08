package com.babacarthiam.patient_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babacarthiam.patient_service.dto.PatientRequestDTO;
import com.babacarthiam.patient_service.dto.PatientResponseDTO;
import com.babacarthiam.patient_service.dto.PatientUpdateDTO;
import com.babacarthiam.patient_service.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Patient API")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok().body(patients);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get a patient by ID")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable String id) {
        return ResponseEntity.ok().body(patientService.getPatientById(id));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a patient by ID")
    public ResponseEntity<PatientResponseDTO> updatePatient(@Validated({Default.class}) @PathVariable String id, @RequestBody PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient by ID")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
