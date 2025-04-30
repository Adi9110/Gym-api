package com.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gym.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByEmail(String email);
}
