package com.gym.request;

import com.gym.entity.Member.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMemberRequest {
    @NotNull(message = "Name cannot be null")
    private String name;
    
    @NotNull(message = "Phone cannot be null")
    private String phone;
    
    @NotNull(message = "Email cannot be null")
    private String email;
    
    private LocalDate dateOfBirth;
    private String address;
    private Gender gender;
    
    // For password change
    private String currentPassword;
    private String newPassword;
}