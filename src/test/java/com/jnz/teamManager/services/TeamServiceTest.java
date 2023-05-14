package com.jnz.teamManager.services;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.TeamRepository;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureTestEntityManager
public class TeamServiceTest {

    @MockBean
    private TeamRepository teamRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    void addTeam(){
        val team = Team.builder().id(1L).name("Hey").description("description").build();
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        Mockito.doNothing().when(userService).addTeamToUser(1L, team.getId());
        teamService.addTeam(team, 1L);
        Mockito.verify(teamRepository).save(team);
    }

    @Test
    void deleteTeam(){
        val user = mock(User.class);
        Mockito.when(user.getUserTeams()).thenReturn(new HashSet<>());
        val users = new HashSet<>(Set.of(
                user
        ));
        val team = Team.builder().id(1L).name("Team test").description("None").players(users).build();
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.ofNullable(team));

        teamService.deleteTeam(1L);
        Mockito.verify(teamRepository).delete(team);
    }

    @Test
    void getTeamsWhereUserIsNotAt(){
        val user = User.builder().id(1L).username("tester").build();
        val teamWithUser = mock(Team.class);
        Mockito.when(teamWithUser.getPlayers()).thenReturn(Set.of(user));
        val teamWithoutUser = mock(Team.class);
        Mockito.when(teamWithoutUser.getPlayers()).thenReturn(new HashSet<>());
        val repoTeams =List.of(
                teamWithUser,
                teamWithoutUser,
                teamWithUser
        );
        Mockito.when(teamRepository.findAll()).thenReturn(repoTeams);
        val res = teamService.getTeamsWhereUserIsNotAt(1L);
        assertFalse(StreamSupport.stream(res.spliterator(), false)
                .map(teamDTO -> modelMapper.map(teamDTO, Team.class))
                .toList().contains(teamWithUser));

    }

    @Test
    void getUsersByTeamId(){
        val users = Set.of(
                User.builder().id(1L).username("user").build(),
                User.builder().id(2L).username("tester").build()
        );

        val team = Team.builder().id(1L).name("Test team").players(users).build();
        Mockito.when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        val res = teamService.getUsersByTeamId(1L);
        assertTrue(StreamSupport.stream(res.spliterator(), false)
                .map(userDTO -> modelMapper.map(userDTO, User.class))
                .toList().containsAll(users));
    }
}
