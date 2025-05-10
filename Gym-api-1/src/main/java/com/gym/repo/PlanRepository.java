package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

}
