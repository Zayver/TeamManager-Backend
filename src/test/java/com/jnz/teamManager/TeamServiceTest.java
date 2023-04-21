package com.jnz.teamManager;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.TeamNotExistsException;
import com.jnz.teamManager.repository.TeamRepository;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {
    
    @Mock
    private TeamRepository teamRepository;
    
    @Mock
    private UserService userService;
    
    @InjectMocks
    private TeamService teamService;
    
    private User user;
    private Team team;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("usertest");
        
        team = new Team();
        team.setId(1L);
        team.setName("Team A");
        team.setPlayers(Set.of(user));
    }
    
    @AfterEach
        void tearDown() {
        teamRepository.deleteAll();
        userService.deleteUser(user.getId());
    }


    @Test
    void testGetTeamById() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        
        Team result = teamService.getTeamById(1L);
        
        assertNotNull(result);
        assertEquals(team, result);
    }
    
    @Test
    void testGetTeamByIdThrowsExceptionWhenTeamNotExists() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(TeamNotExistsException.class, () -> teamService.getTeamById(1L));
    }
    
    @Test
    void testFindAll() {
        when(teamRepository.findAll()).thenReturn(Arrays.asList(team));
        
        Iterable<Team> result = teamService.findAll();
        
        assertNotNull(result);
        assertEquals(1, ((List<Team>) result).size());
        assertEquals(team, ((List<Team>) result).get(0));
    }
    
    @Test
    void testAddTeam() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        
        teamService.addTeam(team, user.getId());
        
        verify(teamRepository, times(1)).save(any(Team.class));
        verify(userService, times(1)).addTeamToUser(eq(user.getId()), eq(team.getId()));
    }
    
    @Test
    void testDeleteTeam() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        
        teamService.deleteTeam(1L);
        
        verify(teamRepository, times(1)).delete(team);
        assertTrue(user.getUserTeams().isEmpty());
    }
    
    @Test
    void testGetUsersByTeamId() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        
        Iterable<User> result = teamService.getUsersByTeamId(1L);
        
        assertNotNull(result);
        assertEquals(1, ((List<User>) result).size());
        assertEquals(user, ((List<User>) result).get(0));
    }

    public void testUpdateTeam() {
        Team team = new Team();
        team.setName("Team A");
        teamRepository.save(team);

        team.setName("Team B");

        teamService.updateTeam(team);

        Team updatedTeam = teamService.getTeamById(team.getId());

        assertEquals("Team B", updatedTeam.getName());
    }

    @Test
    public void testGetTeamsWhereUserIsNotAt() {
        Team team1 = new Team();
        team1.setName("Team A");
        teamRepository.save(team1);

        Team team2 = new Team();
        team2.setName("Team B");
        teamRepository.save(team2);

        User user1 = new User();
        user1.setUsername("test1");
        user1.setId(2L);
        user1.setEmail("test1@test.com");
        user1.setPassword("test1");
        userService.addUser(user1);

        User user2 = new User();
        user2.setUsername("test");
        user2.setId(1L);
        user2.setEmail("test@test.com");
        user2.setPassword("test");
        userService.addUser(user2);
                
        user1 = userService.getUserById(user1.getId());
        user2 = userService.getUserById(user2.getId());

        // add user1 to team1
        userService.addTeamToUser(user1.getId(), team1.getId());

        // get teams where user2 is not at
        Iterable<Team> teams = teamService.getTeamsWhereUserIsNotAt(user2.getId());

        Set<Long> teamIds = new HashSet<>();
        for (Team team : teams) {
            teamIds.add(team.getId());
        }

        assertTrue(teamIds.contains(team1.getId()));
        assertTrue(teamIds.contains(team2.getId()));
    }
}