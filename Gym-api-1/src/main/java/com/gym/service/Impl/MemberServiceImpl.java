package com.gym.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Member;
import com.gym.repo.MemberRepository;
import com.gym.service.MemberService;

@Service
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
	public void deleteMemberById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return memberRepository.findAll();
	}

	@Override
	public Member updateMember(Member member) {
		// TODO Auto-generated method stub
		return memberRepository.save(member);
	}

	@Override
	public Optional<Member> findMemberById(Integer member_id) {
	    return memberRepository.findById(member_id);
	}
}
