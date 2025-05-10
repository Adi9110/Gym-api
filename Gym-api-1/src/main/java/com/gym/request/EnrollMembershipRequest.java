package com.gym.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollMembershipRequest {
    @NotNull
    private Integer planId;
    
    private Double height;
    private Double weight;
    
    @NotNull
    private Double paidAmount;
    
    @NotBlank
    private String paymentMethod;

}