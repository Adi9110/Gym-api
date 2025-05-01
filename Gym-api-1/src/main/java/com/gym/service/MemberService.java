package com.gym.service;

import com.gym.entity.Member;

public interface MemberService {
 
	public Member createMember(Member member);
	
	 long countMembers();
	
	public Member findByEmail(String email);
	
	public Member updateMember(Integer id);
	
	public void deleteMemberById(Integer id);
	
	
}
