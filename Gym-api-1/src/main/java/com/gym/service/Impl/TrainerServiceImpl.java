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
		// TODO Auto-generated method stub
		if (trainerRepository.existsByEmail(trainer.getEmail())) {
//            throw new DuplicateEmailException("Trainer email already exists");
        }
        return trainerRepository.save(trainer);
    }
	}


