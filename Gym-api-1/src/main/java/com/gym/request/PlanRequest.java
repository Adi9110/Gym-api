package com.gym.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PlanRequest {

	@NotBlank
    private String name;
    
    private String description;
    
    @Positive
    private double price;
    
    @Min(1)
    private int durationInDays;
    
    private boolean isActive = true;
}
