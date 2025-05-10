package com.gym.exception;

import org.springframework.http.HttpStatus;

public class TrainerException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    public TrainerException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST; // Default to BAD_REQUEST
    }

    public TrainerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus != null ? httpStatus : HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}