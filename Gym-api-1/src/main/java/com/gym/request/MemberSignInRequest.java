package com.gym.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSignInRequest {
	
		@NotBlank(message = "Email is required")
		private String email;
		
		@NotBlank(message = "Password is required")
		private String password;
}
