package com.gym.gym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gym.dtos.request.CreateCoachDto;
import com.gym.gym.services.AdminService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/CreateCoach")
    public  ResponseEntity<?> CreateCoach(@RequestBody CreateCoachDto entity) {
        

        
        return ResponseEntity.ok(adminService.addCoach(entity));
    }
    
    
}