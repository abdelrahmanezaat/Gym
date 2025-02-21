package com.gym.gym.repo;

import com.gym.gym.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);  // Find role by name (e.g., "USER")
}
