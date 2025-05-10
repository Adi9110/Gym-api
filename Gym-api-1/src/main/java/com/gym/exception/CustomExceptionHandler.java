package com.gym.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<Map<String, String>> memberExceptionHandler(MemberException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Member Exception");
        response.put("message", ex.getMessage());
        response.put("status", "failed");
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
    
    @ExceptionHandler(TrainerException.class)
    public ResponseEntity<Map<String, String>> trainerExceptionHandler(TrainerException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Trainer Exception");
        response.put("message", ex.getMessage());
        response.put("status", "failed");
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
    
    @ExceptionHandler(PlanException.class)
    public ResponseEntity<Map<String, String>> planExceptionHandler(PlanException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Plan Exception");
        response.put("message", ex.getMessage());
        response.put("status", "failed");
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred");
        response.put("status", "failed");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

