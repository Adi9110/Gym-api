package com.gym.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "membership")
public class Membership {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    
	    @ManyToOne
	    @JoinColumn(name = "member_id", nullable = false)
	    private Member member;
	   
	    private LocalDate startDate;
	    
	    private LocalDate endDate;
	    
	    private boolean isActive;
	    
	    private double paidAmount;
	    
	    private String paymentMethod;
	    
}


