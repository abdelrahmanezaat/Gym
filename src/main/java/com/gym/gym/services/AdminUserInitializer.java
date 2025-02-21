package com.gym.gym.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gym.gym.models.Role;
import com.gym.gym.models.User;
import com.gym.gym.repo.RoleRepository;
import com.gym.gym.repo.UserRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository rolerepo;

    @Override
    public void run(String... args) throws Exception {
        // Ensure the "admin" role exists
        AddRole("ADMIN");
        AddRole("USER");

        // Check if an admin user already exists
        if (userRepository.findByEmail("abdelrahmanezzat@gmail.com").isEmpty()) {
            // Create a new admin user
            User admin = new User();
            admin.setEmail("abdelrahmanezzat@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Encrypt the password
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(rolerepo.findByName("ADMIN")); // Assign the "admin" role
            admin.setRoles(adminRoles);
            admin.setAge(22);
            admin.setPhoneNumber("01212556358");
            admin.setAddress("abdgdikskaka");

            // Save the admin user to the database
            userRepository.save(admin);
            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    public void AddRole(String name) {
        if (rolerepo.findByName(name) == null) {
            Role r = new Role();
            r.setName(name);
            rolerepo.save(r);
            System.out.println(name+" role created successfully!");
        } else {
            System.out.println(name+" role already exists.");
        }
    }
}