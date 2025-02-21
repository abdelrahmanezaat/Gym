package com.gym.gym.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gym.gym.dtos.request.ClientDto;
import com.gym.gym.models.Client;
import com.gym.gym.models.Group;
import com.gym.gym.models.User;
import com.gym.gym.repo.ClientRepository;


@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private GroupService groupService;

  @Autowired
  private UserDetailsServiceImpl userDetailsServiceImpl;

  public String JoinToGroup(ClientDto dto,long userId) {
    Client client=new Client();
    Group group=groupService.findGroup(dto.groupId);
    if(group==null)
    return "not found";
    User user=userDetailsServiceImpl.findUser(userId);
    if(user==null)
    return "not found";
    client.setHeight(dto.getHeight());
    client.setWeight(dto.getWeight());
    client.setGroup(group);
    client.setUser(user);
    clientRepository.save(client);
    return "you are client in group now";
    


  }

}
