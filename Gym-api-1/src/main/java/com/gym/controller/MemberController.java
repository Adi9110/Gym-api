package com.gym.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.entity.Member;
import com.gym.exception.MemberException;
import com.gym.request.MemberSignInRequest;
import com.gym.request.MemberSignUpRequest;
import com.gym.service.MemberService;
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
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/public/signup")
	ResponseEntity<?> memberSignUp(@Valid @RequestBody MemberSignUpRequest request, BindingResult bindingResult) {

		System.out.println(request);
		
		if (bindingResult.hasErrors()) {
			throw new MemberException("Invalid User Input", HttpStatus.BAD_REQUEST);
		}

		Member findMember = memberService.findByEmail(request.getEmail());

		if (findMember != null) {
			throw new MemberException("Email ALready Exists", HttpStatus.CONFLICT);
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
			throw new MemberException("Invalid User Input", HttpStatus.BAD_REQUEST);
		}

		Member member = memberService.findByEmail(request.getEmail());

		if (member == null) {
			throw new MemberException("Email Not Found", HttpStatus.NOT_FOUND);
		}
		
		if(!passwordEncoder.matches(request.getPassword(), member.getPassword()))
		{
			throw new MemberException("Invalid Password", HttpStatus.UNAUTHORIZED);
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
	
	@GetMapping("/secure/profile")
	ResponseEntity<?> getUserProfile(@AuthenticationPrincipal CustomerDetails memberDetails) {
		Member member = memberDetails.getUser();
		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("member", member);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
