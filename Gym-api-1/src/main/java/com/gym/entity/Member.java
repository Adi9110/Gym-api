package com.gym.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="members")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	
	@Column(nullable = false, unique=true)
	private String email;
	
	@Column(nullable = false)
    private String phone;
    
    private LocalDate dateOfBirth;
    
    private String address;
     
    private String password;
    
    @Column(nullable=false)
    private String role = "MEMBER"; // Default role
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Membership> memberships;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
   
}
