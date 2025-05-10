package com.gym.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Membership;
import com.gym.repo.MembershipRepository;
import com.gym.service.MembershipService;

@Service
public class MembershipServiceImpl implements MembershipService {
    
    @Autowired
    private MembershipRepository membershipRepository;

	public boolean hasActiveMembership(Integer memberId) {
		// TODO Auto-generated method stub
		 List<Membership> activeMemberships = membershipRepository.findByMemberIdAndIsActive(memberId, true);
         return !activeMemberships.isEmpty();
	}

	
	public Membership createMembership(Membership membership) {
		// TODO Auto-generated method stub
		return membershipRepository.save(membership);
	}


	@Override
	public List<Membership> findByMemberId(Integer memberId, Boolean active) {
		// TODO Auto-generated method stub
		List<Membership> allMemberships = membershipRepository.findByMemberIdAndIsActive(memberId, true);
		return allMemberships;
	}


	@Override
	public List<Membership> findByStatus(Boolean active) {
		// TODO Auto-generated method stub
		return membershipRepository.findByActiveStatus(active);
	}


	@Override
	public List<Membership> findAllMemberships() {
		// TODO Auto-generated method stub
		return membershipRepository.findAll();
	}


	@Override
	public List<Membership> findByMemberId(Integer memberId) {
		// TODO Auto-generated method stub
		List<Membership> allMemberships = membershipRepository.findByMemberId(memberId);
		return allMemberships;
	}


	@Override
	public Optional<Membership> findByMembershipId(Integer membershipId) {
		// TODO Auto-generated method stub
		return  membershipRepository.findById(membershipId);
	}


	@Override
	public void updateMembership(Membership membership) {
		// TODO Auto-generated method stub
		 membershipRepository.save(membership);
	}

 
}