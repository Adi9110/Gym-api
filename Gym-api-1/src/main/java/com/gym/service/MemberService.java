package com.gym.service;

import com.gym.entity.Member;

public interface MemberService {
 
	public Member createMember(Member member);
	public Member updateMember(Integer id);
	public void deleteMemberById(Integer id);
	
	
	
//	public User update(User user);
//	  public User findById(Integer id);
//	  public User findByEmail(String email);
//	  public Page<User> findAll(String searchText ,Pageable pageable);
//	  public void deleteById(Integer id);
	
}
