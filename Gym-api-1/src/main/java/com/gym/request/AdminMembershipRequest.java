package com.gym.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AdminMembershipRequest {

	    @NotNull
	    private Integer memberId;
	    
	    @NotNull
	    private Integer planId;
	    
	    private LocalDate startDate;
	    
	    private Double height;
	    private Double weight;
	    
	    @Positive
	    private Double paidAmount;
	    
	    @NotBlank
	    private String paymentMethod;
	    
	    private boolean replaceExisting = true;
}
