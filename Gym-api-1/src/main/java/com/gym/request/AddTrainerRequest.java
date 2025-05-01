package com.gym.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddTrainerRequest {

	@NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    @NotBlank(message= "Specialization iis required")
    private String specialization;
    
    @Min(value = 0, message = "Experience years cannot be negative")
    private int experienceYears;
}
