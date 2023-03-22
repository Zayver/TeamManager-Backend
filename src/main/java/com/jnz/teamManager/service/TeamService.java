package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    public List<Team> findAll(){
        return teamRepository.findAll();
    }

    public Team addTeam(Team team){
        return teamRepository.save(team);
    }

    public void deleteTeam(Team team){
        teamRepository.delete(team);
    }

    public Team updateTeam(Team team){
        return teamRepository.save(team);
    }
}
