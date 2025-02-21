package com.gym.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.gym.dtos.request.CreateCoachDto;
import com.gym.gym.models.Coach;
import com.gym.gym.repo.CoachRepository;

@Service
public class AdminService {
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    
    
    public String addCoach(CreateCoachDto model){
        Coach coach =new Coach();
        coach.setUser(userDetailsServiceImpl.findUser(model.getUserId()));
        coachRepository.save(coach);
        return"Coach created successfully!";

    }

}