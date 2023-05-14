package com.jnz.teamManager.controller;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
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

    @GetMapping("/all")
    public Iterable<UserDTO> getAllUsersExceptC(@RequestParam("id") Long id){
        return userService.getAllUsersExceptCaller(id);
    }

    @GetMapping("/teams")
    public Iterable<TeamDTO> getTeamsByUserId(@RequestParam("id") Long id){
        return userService.getTeamsByUserId(id);
    }
}