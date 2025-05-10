package com.gym.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.entity.Member;
import com.gym.entity.Membership;
import com.gym.entity.Plan;
import com.gym.exception.MemberException;
import com.gym.exception.PlanException;
import com.gym.request.EnrollMembershipRequest;
import com.gym.request.MemberSignInRequest;
import com.gym.request.MemberSignUpRequest;
import com.gym.request.UpdateMemberRequest;
import com.gym.service.MemberService;
import com.gym.service.MembershipService;
import com.gym.service.PlanService;
import com.gym.service.Impl.CustomerDetails;
import com.gym.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MemberService memberService;
	
	@Autowired
    private MembershipService membershipService;
	
	@Autowired
	private PlanService planService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/public/signup")
	ResponseEntity<?> memberSignUp(@Valid @RequestBody MemberSignUpRequest request, BindingResult bindingResult) {

		System.out.println(request);
		
		if (bindingResult.hasErrors()) {
			throw new MemberException("Invalid User Input");
		}

		Member findMember = memberService.findByEmail(request.getEmail());

		if (findMember != null) {
			throw new MemberException("Email ALready Exists");
		}

		Member member = new Member();
		member.setEmail(request.getEmail());
		member.setName(request.getName());
		member.setPhone(request.getPhone());
		member.setPassword(passwordEncoder.encode(request.getPassword()));
		
		if (memberService.countMembers() == 0) {
            member.setRole("ADMIN"); // First user becomes admin
        } else {
            member.setRole("MEMBER"); // Subsequent users are regular members
        }

		member= memberService.createMember(member);

		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("role", member.getRole().toLowerCase());
		claims.put("memberId", member.getId());
		
		String token = jwtUtil.generateToken(member.getEmail(), claims);
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", "Account created Successfully");
		response.put("token", token);
		response.put("role", member.getRole());
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping("/public/signin")
	ResponseEntity<?> memberSignIn(@Valid @RequestBody MemberSignInRequest request, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new MemberException("Invalid User Input");
		}

		Member member = memberService.findByEmail(request.getEmail());

		if (member == null) {
			throw new MemberException("Email Not Found");
		}
		
		if(!passwordEncoder.matches(request.getPassword(), member.getPassword()))
		{
			throw new MemberException("Invalid Password");
		}
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());
		claims.put("role", member.getRole().toLowerCase()); // Use actual role from member
		claims.put("memberId", member.getId());
		
		String token = jwtUtil.generateToken(member.getEmail(), claims);
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("message", "Logged In Successfully");
		response.put("token", token);
		response.put("role", member.getRole()); 
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/secure/update")
	@PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateMember(
            @AuthenticationPrincipal CustomerDetails memberDetails,
            @Valid @RequestBody UpdateMemberRequest request,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            throw new MemberException("Invalid update data");
        }

        Member currentMember = memberDetails.getUser();
        
        // Update only allowed fields (exclude role from updating)
        currentMember.setName(request.getName());
        currentMember.setPhone(request.getPhone());
        currentMember.setEmail(request.getEmail());
        currentMember.setDateOfBirth(request.getDateOfBirth());
        currentMember.setAddress(request.getAddress());
        currentMember.setGender(request.getGender());
        
        // Optional password update
        if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
            if (!passwordEncoder.matches(request.getCurrentPassword(), currentMember.getPassword())) {
                throw new MemberException("Current password is incorrect");
            }
            currentMember.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        Member updatedMember = memberService.updateMember(currentMember);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Profile updated successfully");
        response.put("member", updatedMember);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }   
	
	
	@GetMapping("/public/plans")
    public ResponseEntity<?> getAllPlans() {
        List<Plan> Plans = planService.findAllPlans();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("plans", Plans);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	  @PostMapping("/secure/enroll")
	    public ResponseEntity<?> enrollInPlan(
	            @AuthenticationPrincipal CustomerDetails memberDetails,
	            @Valid @RequestBody EnrollMembershipRequest request) {
	        
	        Member member = memberDetails.getUser();
	        Plan plan = planService.findPlanById(request.getPlanId());
	        
	        // Check if member already has an active membership
	        List<Membership> activeMembership = membershipService.findByMemberId(member.getId());
	        if (activeMembership==null) {
	            throw new MemberException("You already have an active membership");
	        }
	        
	        Membership membership = new Membership();
	        membership.setMember(member);
	        membership.setPlan(plan);
	        membership.setStartDate(LocalDate.now());
	        membership.setEndDate(LocalDate.now().plusDays(plan.getDurationInDays()));
	        membership.setActive(true);
	        membership.setPaidAmount(plan.getPrice());
	        membership.setPaymentMethod(request.getPaymentMethod());
	        
	        Membership savedMembership = membershipService.createMembership(membership);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("message", "Successfully enrolled in plan");
	        response.put("membership", savedMembership);
	        
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    }
	  
	  @GetMapping("/secure/memberships")
	    public ResponseEntity<?> getMyMemberships(@AuthenticationPrincipal CustomerDetails memberDetails) {
	        Member member = memberDetails.getUser();
	        List<Membership> memberships = membershipService.findByMemberId(member.getId());
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("memberships", memberships);
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	
	
	@GetMapping("/secure/profile")
	ResponseEntity<?> getUserProfile(@AuthenticationPrincipal CustomerDetails memberDetails) {
		Member member = memberDetails.getUser();
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("member", member);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
