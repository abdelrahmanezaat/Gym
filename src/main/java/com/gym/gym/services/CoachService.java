package com.gym.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.gym.dtos.request.GroupDto;
import com.gym.gym.dtos.response.GroupRespo;
import com.gym.gym.models.Coach;
import com.gym.gym.models.Group;
import com.gym.gym.repo.CoachRepository;
import com.gym.gym.repo.GroupRepository;

@Service
public class CoachService {
    @Autowired
    CoachRepository coachRepository;

    @Autowired
    GroupRepository groupRepository;

    public GroupRespo CreateCoach(GroupDto dto,long UserId) {
        Coach coach = findCoach(UserId);
        if(coach==null)
        return null;
        Group group = new Group();
        group.setDay(dto.getDay());
        group.setEndTime(dto.getEndTime());
        group.setStartTime(dto.getStartTime());
        group.setCoach(coach);
        groupRepository.save(group);


        GroupRespo groupRespo = new GroupRespo();
        groupRespo.setCoachId(coach.getId());
        groupRespo.setDay(group.getDay());
        groupRespo.setEndTime(group.getEndTime());
        groupRespo.setStartTime(group.getStartTime());
        groupRespo.setId(group.getId());
        return groupRespo;
    }

    public Coach findCoach(long id) {
        return coachRepository.findByUserId(id).orElse(null);
    }
   
}