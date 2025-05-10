package com.gym.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym.entity.Member;
import com.gym.entity.Membership;
import com.gym.entity.Plan;
import com.gym.entity.Trainer;
import com.gym.exception.TrainerException;
import com.gym.request.PlanRequest;
import com.gym.request.AddTrainerRequest;
import com.gym.request.AdminMembershipRequest;
import com.gym.service.MemberService;
import com.gym.service.MembershipService;
import com.gym.service.PlanService;
import com.gym.service.TrainerService;
import com.gym.service.Impl.CustomerDetails;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

	    @Autowired
	    private TrainerService trainerService;
	    
	    @Autowired
	    private MemberService memberService;
	    
	    @Autowired
	    private MembershipService membershipService;

	    @Autowired
	    private  PlanService planService;
	   
	    @PostMapping("/secure/addTrainers")
	    @PreAuthorize("hasRole('admin')")
	    public ResponseEntity<?> addTrainer( @AuthenticationPrincipal CustomerDetails adminDetails, 
	    		@Valid @RequestBody AddTrainerRequest request,
	           BindingResult bindingResult) {
	        
	        // Verify the authenticated user is an admin
	        //Member admin = adminDetails.getUser();
	        //if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
	        //    throw new TrainerException("Access Denied");
	        //}

	        if (bindingResult.hasErrors()) {
	            throw new TrainerException("Invalid Trainer Data");
	        }

	        Trainer trainer = new Trainer();
	        trainer.setName(request.getName());
	        trainer.setEmail(request.getEmail());
	        trainer.setPhone(request.getPhone());
	        trainer.setSpecialization(request.getSpecialization());
	        trainer.setExperienceYears(request.getExperienceYears());

	        Trainer createdTrainer = trainerService.createTrainer(trainer);

	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Trainer added successfully");
	        response.put("trainer", createdTrainer);
	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	    //===========================MEMBERSHIP=============================================
	 
	    @PostMapping("/secure/addMembership")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> addMembership(@Valid @RequestBody AdminMembershipRequest request) {
	        
	        Member member = memberService.findMemberById(request.getMemberId())
	                .orElseThrow(() -> new RuntimeException("Member not found"));
	        
	        Plan plan = planService.findPlanById(request.getPlanId());

	        // Create new membership
	        Membership membership = new Membership();
	        membership.setMember(member);
	        membership.setPlan(plan);
	        membership.setStartDate(LocalDate.now());
	        membership.setEndDate(LocalDate.now().plusDays(plan.getDurationInDays()));
	        membership.setActive(true);
	        membership.setPaidAmount(plan.getPrice()); // Use plan price by default
	        membership.setPaymentMethod(request.getPaymentMethod());
	        
	       
	        Membership savedMembership = membershipService.createMembership(membership);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Membership added successfully");
	        response.put("membership", savedMembership);
	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }

	    @GetMapping("/secure/memberships/{memberId}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<List<Membership>> getMemberMemberships(@PathVariable Integer memberId) {
	        List<Membership> memberships = membershipService.findByMemberId(memberId);
	        return new ResponseEntity<>(memberships, HttpStatus.OK);
	    }

	    
	    @GetMapping("/secure/allMemberships")
	    @PreAuthorize("hasRole('admin')")
	    public ResponseEntity<List<Membership>> getAllMemberships(
	            @RequestParam(required = false) Integer memberId,
	            @RequestParam(required = false) Boolean active) {
	        
	        List<Membership> memberships;
	        if (memberId != null) {
	            memberships = membershipService.findByMemberId(memberId, active);
	        } else if (active != null) {
	            memberships = membershipService.findByStatus(active);
	        } else {
	            memberships = membershipService.findAllMemberships();
	        }
	        
	        return new ResponseEntity<>(memberships, HttpStatus.OK);
	    }
	    
	    @PatchMapping("/secure/memberships/{membershipId}/cancel")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> cancelMembership(@PathVariable Integer membershipId) {
	        Membership membership = membershipService.findByMembershipId(membershipId)
	                .orElseThrow(() -> new RuntimeException("Membership not found"));
	        
	        membership.setActive(false);
	        membershipService.updateMembership(membership);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Membership cancelled");
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    
	    //===========================MEMBERS================================================ 
	    @GetMapping("/secure/allMembers")
	    @PreAuthorize("hasRole('admin')")
	    public ResponseEntity<?> getAllMembers() {
	        List<Member> members = memberService.getAllMembers();
	        
	        List<Map<String, Object>> memberResponses = members.stream()
	                .map(member -> {
	                    Map<String, Object> memberMap = new HashMap<>();
	                    memberMap.put("id", member.getId());
	                    memberMap.put("name", member.getName());
	                    memberMap.put("email", member.getEmail());
	                    memberMap.put("phone", member.getPhone());
	                    memberMap.put("dateOfBirth", member.getDateOfBirth());
	                    memberMap.put("address", member.getAddress());
	                    memberMap.put("gender", member.getGender());
	                    return memberMap;
	                })
	                .collect(Collectors.toList());

	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("members", memberResponses);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    //================PLANS========================================================
	   
	    @PostMapping("/secure/addPlans")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> addPlan(@Valid @RequestBody PlanRequest request) {
            Plan plan = new Plan();
            plan.setName(request.getName());
            plan.setDescription(request.getDescription());
            plan.setPrice(request.getPrice());
            plan.setDurationInDays(request.getDurationInDays());
            plan.setActive(request.isActive());
            
            Plan savedPlan = planService.savePlan(plan);
            
            return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
    	}
	    
	    @PutMapping("/secure/updatePlan/{planId}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> updatePlan(
	            @PathVariable Integer planId,
	            @Valid @RequestBody PlanRequest request) {
	        
	    	 Plan plan = planService.findPlanById(planId);
	    	           
	        
	        plan.setName(request.getName());
	        plan.setDescription(request.getDescription());
	        plan.setPrice(request.getPrice());
	        plan.setDurationInDays(request.getDurationInDays());
	        plan.setActive(request.isActive());
	        
	        Plan updatedPlan = planService.savePlan(plan);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Plan Updated Successfully");
	        response.put("updated plan",updatedPlan);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    @GetMapping("/secure/getAllPlans")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<List<Plan>> getAllPlans() {
	        List<Plan> plans = planService.findAllPlans();
	        return ResponseEntity.ok(plans);
	    }
	    
	    // Toggle plan status
	    @PatchMapping("/{planId}/status")
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<?> togglePlanStatus(@PathVariable Integer planId) {
	        Plan plan = planService.findPlanById(planId);
	        
	        plan.setActive(!plan.isActive());
	        planService.savePlan(plan);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Plan status Updated!");
	        response.put("newStatus", plan.isActive());
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	}
	    
	    


    

 
	   
	    
	   
	   

	       

	    	
	    
	    
	    
	

