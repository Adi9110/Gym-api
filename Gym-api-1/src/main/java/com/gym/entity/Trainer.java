package com.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "trainers")
public class Trainer {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    
	    @Column(nullable = false)
	    private String name;
	  
	    
	    @Column(nullable = false, unique = true)
	    private String email;
	    
	    @Column(nullable = false)
	    private String phone;
	    
	    private String specialization;
	    
	    private int experienceYears;
	    
	    
}


