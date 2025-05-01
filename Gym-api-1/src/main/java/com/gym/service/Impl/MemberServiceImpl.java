package com.gym.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gym.entity.Member;
import com.gym.repo.MemberRepository;
import com.gym.service.MemberService;

public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public Member createMember(Member member) {
		// TODO Auto-generated method stub
		return memberRepository.save(member);
	}
	
	@Override
    public long countMembers() {
        return memberRepository.count();
    }

	@Override
	public Member findByEmail(String email) {
		// TODO Auto-generated method stub
		return memberRepository.findByEmail(email).orElse(null);
	}

	@Override
	public Member updateMember(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMemberById(Integer id) {
		// TODO Auto-generated method stub
		
	}
     
}
