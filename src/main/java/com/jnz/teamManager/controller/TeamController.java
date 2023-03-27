package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.TeamService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping("/all")
    public Iterable<Team> findAll(){
        return teamService.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeam(@RequestBody Team team){
        teamService.addTeam(team);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@RequestBody Team team){
        teamService.deleteTeam(team);
    }

    @GetMapping("/users/{id}")
    public Iterable<User> getUsersByTeamId(@PathVariable("id") Long id){
        return teamService.getUsersByTeamId(id);
    }




}
