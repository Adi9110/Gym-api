package com.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>{

	boolean existsByEmail(String email);
}
