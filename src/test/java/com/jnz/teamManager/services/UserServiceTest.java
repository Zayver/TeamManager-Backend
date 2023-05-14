package com.jnz.teamManager.services;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.TeamNotExistsException;
import com.jnz.teamManager.exception.error.UserNotExistsException;
import com.jnz.teamManager.exception.error.UsernameAlreadyExistsException;
import com.jnz.teamManager.repository.TeamRepository;
import com.jnz.teamManager.repository.UserRepository;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TeamRepository teamRepository;


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void getUsernameThatExists(){
        Mockito.when(userRepository.findAll()).thenReturn(List.of(User.builder()
                .username("tester").password("1234").email("tester@correo.com").build()));
        val user = userService.getUserByUsername("tester");
        assertEquals("tester", user.getUsername());
    }

    @Test
    void getUserThatNotExists(){
        try{
            val user = userService.getUserByUsername("hola");
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void saveUser() {
        val user = User.builder()
                .email("tester@correo.com")
                .username("tester")
                .password("123456")
                .build();
        this.userService.addUser(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void addUserThatAlreadyExists(){
        val user = User.builder()
                .email("tester@correo.com")
                .username("tester")
                .password("123456")
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        try{
            userService.addUser(user);
        }catch (UsernameAlreadyExistsException e){
            return;
        }
        fail();
    }

    @Test
    void deleteUser(){
        val user = User.builder().id(1L).email("tester@correo.com").username("tester").password("123456").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void deleteUserThatDoesntExist(){
        try{
            userService.deleteUser(1L);
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void addTeamToUser(){
        val user = User.builder().id(1L).email("tester@correo.com").username("tester").password("123456").userTeams(new HashSet<>()).build();
        val team = Team.builder().id(1L).name("Hey").description("etc").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        userService.addTeamToUser(1L, 1L);
        Mockito.verify(userRepository).save(user);
        Mockito.verify(userRepository).findById(1L);

    }

    @Test
    void addTeamToUser_NoUserExists(){
        val team = Team.builder().id(1L).name("Hey").description("etc").build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        try{
            userService.addTeamToUser(1L, 1L);
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void addTeamToUser_NoTeamExists(){
        val user = User.builder().id(1L).email("tester@correo.com").username("tester").password("123456").userTeams(new HashSet<>()).build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        try{
            userService.addTeamToUser(1L, 1L);
        }catch (TeamNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void getTeamsByUserId(){
        val teams = Set.of(Team.builder().id(1L).name("Hey").description("etc").build(),
                Team.builder().id(2L).name("Hey2").description("etc2").build()
        );
        val user = User.builder().id(1L).email("tester@correo.com").username("tester").password("123456")
                .userTeams(teams).build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        val res = userService.getTeamsByUserId(1L);

        assertTrue(StreamSupport.stream(res.spliterator(), false)
                .map(teamDTO -> modelMapper.map(teamDTO, Team.class))
                .toList().containsAll(teams));
    }

    @Test
    void getTeamsByUserId_IdNotExists(){
        try{
            userService.getTeamsByUserId(1L);
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void getUserByUsername(){
        val user = User.builder().id(1L).email("tester@correo.com").username("tester").password("123456").build();
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        val u = userService.getUserByUsername("tester");
        assertEquals(user, u);

    }

    @Test
    void getUserByUsername_NotExists(){
        try{
            userService.getUserByUsername("tester");
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void getAllUsersExceptCaller(){
        val user = User.builder().id(1L).username("tester").email("tester@correo.com").build();
        val users = new ArrayList<>(List.of(
                user,
                User.builder().id(2L).username("admin").email("admin@correo.com").build(),
                User.builder().id(3L).username("hola").email("gola@correo.com").build()
        ));
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        val result = userService.getAllUsersExceptCaller(1L);

        val r = StreamSupport.stream(result.spliterator(), false)
                .toList();
        assertFalse(r.contains(modelMapper.map(user, UserDTO.class)));
    }

    @Test
    void getAllUsersExceptCaller_NotExist(){
        try{
            userService.getAllUsersExceptCaller(4L);
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }




}
