package com.gym.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.gym.models.Group;

public interface GroupRepository extends JpaRepository<Group,Long> {
}