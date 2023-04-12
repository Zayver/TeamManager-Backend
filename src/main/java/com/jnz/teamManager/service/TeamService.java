package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.TeamRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    private UserService userService;

    @Autowired
    public void setUserService(@Lazy UserService userService){
        this.userService = userService;
    }

    public Team getTeamById(Long id){
        return teamRepository.findById(id).orElseThrow();
    }

    public Iterable<Team> findAll(){
        return teamRepository.findAll();
    }

    public void addTeam(Team team, Long userId){
        val t = teamRepository.save(team);
        userService.addTeamToUser(userId, t.getId());
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

    public Iterable<Team> getTeamsWhereUserIsNotAt(Long id) {
        return this.teamRepository.findAll().stream()
                .filter(team -> team.getPlayers().stream()
                .noneMatch(user -> user.getId().equals(id)))
                .collect(Collectors.toSet());
    }
}
