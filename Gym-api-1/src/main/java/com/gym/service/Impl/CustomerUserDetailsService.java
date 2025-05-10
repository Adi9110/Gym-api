package com.gym.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.entity.Member;
import com.gym.entity.Trainer;
import com.gym.repo.MemberRepository;
import com.gym.repo.UserRepo;
import com.gym.service.TrainerService;



@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	
//	@Autowired
//	private TrainerService trainerService;
	
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	
//	@Transactional
//	public TrainerDetails loadTrainerUser(String email) throws UsernameNotFoundException{
//		
//		Trainer trainer = trainerService.findByEmail(email);
//		if(trainer ==null)
//		{
//			throw new UsernameNotFoundException("User Not Found");
//		}
//		return new TrainerDetails(trainer);
//		
//	}
	
	
	@Transactional
	public CustomerDetails loadCustomer(String email) {
		
		Member  member = memberRepository.findByEmail(email).orElse(null);
		if(member == null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		return new  CustomerDetails(member);
			
	}
	
//	@Transactional
//	public AdminDetails loadAdmin(String email)
//	{
//		Admin admin = AdminService.getByEmail(email);
//
//		if(admin ==null)
//		{
//			throw new UsernameNotFoundException("User Not Found");
//		}
//		
//		return new AdminDetails(deliveryPartner);
//	}

}
