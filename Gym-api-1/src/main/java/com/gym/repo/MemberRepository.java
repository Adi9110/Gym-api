package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
