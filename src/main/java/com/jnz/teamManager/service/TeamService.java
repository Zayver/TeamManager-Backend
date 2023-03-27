package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.TeamRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    public Team getTeamById(Long id){
        return teamRepository.findById(id).orElseThrow();
    }

    public Iterable<Team> findAll(){
        return teamRepository.findAll();
    }

    public void addTeam(Team team){
        teamRepository.save(team);
    }

    public void deleteTeam(Team team){
        teamRepository.delete(team);
    }

    public Iterable<User> getUsersByTeamId(Long id) {
        val team =  teamRepository.findById(id).orElseThrow();
        return team.getPlayers();
    }

}
