package com.jnz.teamManager.service;

import com.jnz.teamManager.dto.RequestDTO;
import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.exception.error.RequestAlreadyExistsException;
import com.jnz.teamManager.repository.RequestRepository;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;


    public void addRequest(Map<String, String> request){
        val user = userService.getUserById(Long.parseLong(request.get("id")));
        val team = teamService.getTeamById(Long.parseLong(request.get("teamId")));

        if(this.requestRepository.findAll()
                .stream().
                anyMatch(request1 -> request1.getTeam()
                        .getId()
                        .equals(team.getId()) && request1.getUser().getId().equals(user.getId()))){
            throw new RequestAlreadyExistsException();
        }

        var r = new Request();
        r.setTeam(team);
        r.setUser(user);
        r.setMessage(request.get("message"));
        requestRepository.save(r);
    }
    public void deleteRequest(Request request){
        requestRepository.delete(request);
    }

    public Iterable<RequestDTO> getRequestByTeamId(Long id) {
        return this.requestRepository.findAll().stream().filter(request -> request.getTeam().getId().equals(id))
                .map(request -> modelMapper.map(request, RequestDTO.class))
                .collect(Collectors.toSet());
    }

    public void acceptRequest(Request request) {
        userService.addTeamToUser(request.getUser().getId(), request.getTeam().getId());
        deleteRequest(request);
    }
}
