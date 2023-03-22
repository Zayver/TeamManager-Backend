package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }
}
