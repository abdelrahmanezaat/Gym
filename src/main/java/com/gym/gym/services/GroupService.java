package com.gym.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.gym.models.Group;
import com.gym.gym.repo.GroupRepository;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;
    public Group findGroup(long id){
        return groupRepository.findById(id).orElse(null);
    }
   
}