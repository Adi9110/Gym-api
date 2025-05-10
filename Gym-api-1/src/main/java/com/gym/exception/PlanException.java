package com.gym.exception;

import org.springframework.http.HttpStatus;

public class PlanException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    public PlanException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST; // Default to BAD_REQUEST
    }

    public PlanException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}