package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.TeamRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;

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

    @Transactional
    public void deleteTeam(Long id){
        val team = getTeamById(id);
        for (var user: team.getPlayers()){
            user.getUserTeams().remove(team);
        }
        teamRepository.delete(team);
    }

    public Iterable<User> getUsersByTeamId(Long id) {
        val team =  getTeamById(id);
        return team.getPlayers();
    }

    public void updateTeam(Team team){
        var tTeam = getTeamById(team.getId());
        tTeam = team;
        teamRepository.save(tTeam);
    }

}
