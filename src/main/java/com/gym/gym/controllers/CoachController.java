package com.gym.gym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gym.dtos.request.GroupDto;
import com.gym.gym.dtos.response.GroupRespo;
import com.gym.gym.security.JwtTokenProvider;
import com.gym.gym.services.CoachService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/Coach")
public class CoachController {
    @Autowired
    CoachService coachService;
    @Autowired 
    JwtTokenProvider jwtTokenProvider;
    

    @PostMapping("/AddGroup")
    
    public ResponseEntity<?> postMethodName(@RequestHeader("Authorization") String token,@RequestBody GroupDto entity) {
        
        
        long id=jwtTokenProvider.getIdFromToken(token.substring(7));
        GroupRespo groupRespo = coachService.CreateCoach(entity,id);
        return ResponseEntity.ok(groupRespo);
    }
}