//package com.gym.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.gym.entity.Member;
//import com.gym.entity.Trainer;
//import com.gym.exception.MemberException;
//import com.gym.request.AddTrainerRequest;
//import com.gym.service.TrainerService;
//import com.gym.service.Impl.CustomerDetails;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/admin")
//
//public class AdminController {
//
//	    @Autowired
//	    private TrainerService trainerService;
//
//	    @PostMapping("/secure/trainers")
//	    public ResponseEntity<?> addTrainer( @AuthenticationPrincipal CustomerDetails adminDetails, 
//	    		@Valid @RequestBody AddTrainerRequest request,
//	           BindingResult bindingResult) {
//	        
//	        // Verify the authenticated user is an admin
//	        Member admin = adminDetails.getUser();
//	        if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
//	            throw new MemberException("Access Denied", HttpStatus.FORBIDDEN);
//	        }
//
//	        if (bindingResult.hasErrors()) {
//	            throw new MemberException("Invalid Trainer Data", HttpStatus.BAD_REQUEST);
//	        }
//
//	        Trainer trainer = new Trainer();
//	        trainer.setName(request.getName());
//	        trainer.setEmail(request.getEmail());
//	        trainer.setPhone(request.getPhone());
//	        trainer.setSpecialization(request.getSpecialization());
//	        trainer.setExperienceYears(request.getExperienceYears());
//
//	        Trainer createdTrainer = trainerService.createTrainer(trainer);
//
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("status", "success");
//	        response.put("message", "Trainer added successfully");
//	        response.put("trainer", createdTrainer);
//	        
//	        return new ResponseEntity<>(response, HttpStatus.CREATED);
//	    }
//	}
//
