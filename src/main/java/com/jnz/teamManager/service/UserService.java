package com.jnz.teamManager.service;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.EmailAlreadyExistsException;
import com.jnz.teamManager.exception.error.UserNotExistsException;
import com.jnz.teamManager.exception.error.UsernameAlreadyExistsException;
import com.jnz.teamManager.repository.UserRepository;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamService teamService;

    private ModelMapper modelMapper;

    @Autowired
    private void setModelMapper(@Lazy ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }



    protected User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(UserNotExistsException::new);
    }

    public User addUser(User user) {
        if(this.userRepository.findAll().stream().anyMatch(user1 -> user1.getUsername().equals(user.getUsername()))){
            throw new UsernameAlreadyExistsException();
        }
        if(this.userRepository.findAll().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))){
            throw new EmailAlreadyExistsException();
        }
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

    public Iterable<TeamDTO> getTeamsByUserId(Long id) {
        val user = getUserById(id);
        return user.getUserTeams().stream().map(team -> modelMapper.map(team, TeamDTO.class)).collect(Collectors.toSet());
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findAll()
                .stream().filter(user -> user.getUsername().equals(username))
                .findFirst().orElseThrow(UserNotExistsException::new);
    }

    public Iterable<UserDTO> getAllUsersExceptCaller(Long id) {
        var users = userRepository.findAll();
        val userToRemove = getUserById(id);
        users.remove(userToRemove);
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toSet());
    }
}
