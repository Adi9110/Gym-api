package com.gym.service;

import java.util.List;
import java.util.Optional;

import com.gym.entity.Member;

public interface MemberService {
 
	public Member createMember(Member member);
	
	public Optional<Member> findMemberById(Integer member_id);
	
	long countMembers();
	
	public Member findByEmail(String email);
	
	public void deleteMemberById(Integer id);
	
	public List<Member> getAllMembers();

	public Member updateMember(Member member);
	
	
}
