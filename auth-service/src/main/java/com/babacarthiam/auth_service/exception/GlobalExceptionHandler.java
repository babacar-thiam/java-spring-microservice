package com.babacarthiam.auth_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public  ResponseEntity<Map<String, String>>  handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        log.error("Email already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        errors.put("message", "Email already exists");

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    
    }

    @ExceptionHandler(UserNotFoundException.class)
    public  ResponseEntity<Map<String, String>>  handlePatientNotFoundException(UserNotFoundException ex) {
        log.error("Email already exists {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        errors.put("message", "User not found");

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    
    }

}
