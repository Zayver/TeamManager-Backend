package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping("/all")
    public List<Team> findAll(){
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

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateTeam(@RequestBody Team team){
        teamService.updateTeam(team);
    }




}
