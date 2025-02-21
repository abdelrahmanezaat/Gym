package com.gym.gym.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.gym.dtos.request.ClientDto;
import com.gym.gym.dtos.request.GroupDto;
import com.gym.gym.dtos.response.GroupRespo;
import com.gym.gym.security.JwtTokenProvider;
import com.gym.gym.services.ClientService;

@RestController
@RequestMapping("/api/Client")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired 
    JwtTokenProvider jwtTokenProvider;
    

    @PostMapping("/JoinGroup")
    
    public ResponseEntity<?> postMethodName(@RequestHeader("Authorization") String token,@RequestBody ClientDto entity) {
        
        
        long id=jwtTokenProvider.getIdFromToken(token.substring(7));
        
        return ResponseEntity.ok(clientService.JoinToGroup(entity, id));
    }

}