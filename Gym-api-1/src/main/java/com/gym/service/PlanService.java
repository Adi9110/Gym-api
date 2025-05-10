package com.gym.service;

import java.util.List;
import java.util.Optional;

import com.gym.entity.Plan;

public interface PlanService {

	Plan savePlan(Plan plan);

	Plan findPlanById(Integer p_id);

	List<Plan> findAllPlans();

	
}
