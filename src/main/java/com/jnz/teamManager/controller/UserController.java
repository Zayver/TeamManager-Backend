package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }
}
