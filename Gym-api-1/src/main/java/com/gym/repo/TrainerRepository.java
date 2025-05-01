package com.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>{

	Optional<Trainer> findByEmail(String email);
}
