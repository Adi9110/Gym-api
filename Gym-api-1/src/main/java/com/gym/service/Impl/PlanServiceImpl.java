package com.gym.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.entity.Plan;
import com.gym.exception.PlanException;
import com.gym.repo.PlanRepository;
import com.gym.service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	
	@Autowired
	private PlanRepository planRepository;

	@Override
	public Plan savePlan(Plan plan) {
		// TODO Auto-generated method stub
		return planRepository.save(plan);
	}

	@Override
	public Plan findPlanById(Integer p_id) {
		// TODO Auto-generated method stub
		Optional<Plan> plan = planRepository.findById(p_id);
		
		if(plan.isPresent()) {
			return plan.get();
		}
		else {
			throw new PlanException("Plan not Found!");
	    }
		
	}
	
	@Override
	public List<Plan> findAllPlans() {
		// TODO Auto-generated method stub
		List<Plan> allPlans = planRepository.findAll();
		return allPlans;
	}
		
		
}

	

	
	

	


	
	
	

	

