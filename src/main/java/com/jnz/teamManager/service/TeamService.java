package com.jnz.teamManager.service;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.TeamNotExistsException;
import com.jnz.teamManager.repository.TeamRepository;
import lombok.val;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private void setModelMapper(@Lazy ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setUserService(@Lazy UserService userService){
        this.userService = userService;
    }

    protected Team getTeamById(Long id){
        return teamRepository.findById(id).orElseThrow(TeamNotExistsException::new);
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


    public void updateTeam(Team team){
        var tTeam = getTeamById(team.getId());
        tTeam = team;
        teamRepository.save(tTeam);
    }

    public Iterable<TeamDTO> getTeamsWhereUserIsNotAt(Long id) {
        return this.teamRepository.findAll().stream()
                .filter(team -> team.getPlayers().stream()
                .noneMatch(user -> user.getId().equals(id)))
                .map(team -> modelMapper.map(team, TeamDTO.class))
                .collect(Collectors.toSet());
    }
}
