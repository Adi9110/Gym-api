package com.gym.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gym.entity.Trainer;
import com.gym.repo.TrainerRepository;
import com.gym.service.TrainerService;

public class TrainerServiceImpl implements TrainerService{

	@Autowired
	private TrainerRepository trainerRepository;
	
	@Override
	public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }
	@Override
	public Trainer findByEmail(String email) {
		// TODO Auto-generated method stub
			return trainerRepository.findByEmail(email).orElse(null);
		}
	}
	


