package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.UserRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageTranscoder;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;



    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        val user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }

    public void addTeamToUser(long id, long teamId) {
        val user = getUserById(id);
        val team = teamService.getTeamById(teamId);
        user.getUserTeams().add(team);
        userRepository.save(user);
    }

    public Iterable<Team> getTeamsByUserId(Long id) {
        val user = getUserById(id);
        return user.getUserTeams();
    }
}
