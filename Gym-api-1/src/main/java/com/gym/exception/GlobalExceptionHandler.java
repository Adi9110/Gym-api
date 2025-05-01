package com.gym.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<Map<String, Object>> handleCanddidateException(MemberException memberException)
	{
		Map<String,Object> errorResponse = new HashMap<>();
		errorResponse.put("status", "failure");
		errorResponse.put("type","User Exception");
		errorResponse.put("error", memberException.getMessage());
		errorResponse.put("localTime", LocalDateTime.now());
		errorResponse.put("status", memberException.getHttpStatus().toString());
		return new ResponseEntity<Map<String,Object>>(errorResponse, memberException.getHttpStatus());
		
	}
	
}
