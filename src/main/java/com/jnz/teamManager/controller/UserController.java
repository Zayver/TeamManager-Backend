package com.jnz.teamManager.controller;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/all/{id}")
    public Iterable<UserDTO> getAllUsersExceptCaller(@PathVariable("id") Long id){
        return userService.getAllUsersExceptCaller(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @GetMapping("/teams/{id}")
    public Iterable<TeamDTO> getTeamsByUserId(@PathVariable("id") Long id){
        return userService.getTeamsByUserId(id);
    }


    @PutMapping("/add_team")
    public void addTeamToUser(@RequestBody Map<String, String> json) {
        val id = Long.parseLong(json.get("id"));
        val teamId = Long.parseLong(json.get("teamId"));
        userService.addTeamToUser(id, teamId);
    }


}