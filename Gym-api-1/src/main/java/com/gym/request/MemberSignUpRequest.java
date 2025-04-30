package com.gym.request;

import lombok.Data;

import jakarta.validation.constraints.*;
@Data
public class MemberSignUpRequest {


	    @NotBlank(message = "Name is required")
	    private String name;

	    @NotBlank(message = "Email is required")
	    private String email;

	    @NotBlank(message = "Phone number is required")
	    private String phone;

	    @NotBlank(message = "Password is required")
	    private String password;
	    	   
}
