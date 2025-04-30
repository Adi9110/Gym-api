package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.MembershipPlan;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Integer>{

}
