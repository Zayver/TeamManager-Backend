package com.jnz.teamManager.controller;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.Team;
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
    public Iterable<TeamDTO> findAll(@RequestParam("id") Long id){
        return teamService.getTeamsWhereUserIsNotAt(id);
    }

    @PostMapping("/add")
    public void addTeam(@RequestBody Team team, @RequestParam("id") Long id){
        teamService.addTeam(team, id);
    }

    @PutMapping("/update")
    public void updateTeam(@RequestBody Team team){
        teamService.updateTeam(team);
    }

    @DeleteMapping("/delete")
    public void deleteTeam(@RequestParam("id") Long id){
        teamService.deleteTeam(id);
    }

    @GetMapping("/users")
    public Iterable<UserDTO> getUsersByTeamId(@RequestParam("id") Long id){
        return teamService.getUsersByTeamId(id);
    }

}
