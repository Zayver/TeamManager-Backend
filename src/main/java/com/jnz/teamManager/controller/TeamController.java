package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public void updateTeam(@RequestBody Team team){
        teamService.updateTeam(team);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable("id") Long id){
        teamService.deleteTeam(id);
    }

    @GetMapping("/users/{id}")
    public Iterable<User> getUsersByTeamId(@PathVariable("id") Long id){
        return teamService.getUsersByTeamId(id);
    }




}
