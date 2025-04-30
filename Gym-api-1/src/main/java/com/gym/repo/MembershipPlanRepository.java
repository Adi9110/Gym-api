package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Plan;

public interface MembershipPlanRepository extends JpaRepository<Plan, Integer>{

}
