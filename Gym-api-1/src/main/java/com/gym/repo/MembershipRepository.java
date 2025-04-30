package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Integer>{

}
