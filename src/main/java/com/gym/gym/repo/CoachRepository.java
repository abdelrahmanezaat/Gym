package com.gym.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.gym.models.Coach;
import com.gym.gym.models.User;

public interface CoachRepository extends JpaRepository<Coach,Long>{
    Optional<Coach> findByUserId(long UserId);
}