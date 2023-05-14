package com.jnz.teamManager.services;

import com.jnz.teamManager.dto.InvitationDTO;
import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.InvitationAlreadyExistsException;
import com.jnz.teamManager.exception.error.RequestAlreadyExistsException;
import com.jnz.teamManager.repository.InvitationsRepository;
import com.jnz.teamManager.service.InvitationsService;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class InvitationServiceTest {

    @MockBean
    private InvitationsRepository invitationsRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private TeamService teamService;

    @Autowired
    private InvitationsService invitationsService;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    void addInvitation(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val user2 = User.builder().id(2L).username("admin").build();

        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", 1L)).thenReturn(user);
        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", 2L)).thenReturn(user2);
        Mockito.when(ReflectionTestUtils.invokeMethod(teamService, "getTeamById", anyLong())).thenReturn(team);
        invitationsService.addInvitation(Map.of(
                "userReceiverId", "1",
                "userOwnerId", "2",
                "teamId", "1"
        ));
        Mockito.verify(invitationsRepository).save(Mockito.any());
    }

    @Test
    void addInvitation_AlreadyExists(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val user2 = User.builder().id(2L).username("admin").build();

        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", 1L)).thenReturn(user);
        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", 2L)).thenReturn(user2);
        Mockito.when(ReflectionTestUtils.invokeMethod(teamService, "getTeamById", anyLong())).thenReturn(team);

        val invs = List.of(
                Invitation.builder().id(1L).teamId(team).user(user).userOwner(user2).build()
        );
        Mockito.when(invitationsRepository.findAll()).thenReturn(invs);

        try{
            invitationsService.addInvitation(Map.of(
                    "userReceiverId", "1",
                    "userOwnerId", "2",
                    "teamId", "1"
            ));
        }catch (InvitationAlreadyExistsException e){
            return;
        }
        fail();
    }

    @Test
    void acceptInvitation(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val user2 = User.builder().id(2L).username("admin").build();
        Mockito.doNothing().when(userService).addTeamToUser(Mockito.anyLong(), Mockito.anyLong());
        val inv = Invitation.builder().id(1L).teamId(team).user(user).userOwner(user2).build();
        invitationsService.acceptInvitation(inv);
        Mockito.verify(invitationsRepository).delete(inv);
    }

    @Test
    void deleteInvitation(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val user2 = User.builder().id(2L).username("admin").build();
        val inv = Invitation.builder().id(1L).teamId(team).user(user).userOwner(user2).build();
        invitationsService.deleteInvitation(inv);
        Mockito.verify(invitationsRepository).delete(inv);
    }

    @Test
    void getInvitationById(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val user2 = User.builder().id(2L).username("admin").build();

        val inv = Invitation.builder().id(1L).teamId(team).user(user).userOwner(user2).build();
        val invs = List.of(
                inv,
                Invitation.builder().id(2L).teamId(team).user(user2).userOwner(user).build()
        );
        Mockito.when(invitationsRepository.findAll()).thenReturn(invs);
        val res = invitationsService.getInvitationsById(1L);
        assertTrue(StreamSupport.stream(res.spliterator(), false)
                .map(invDTO -> modelMapper.map(invDTO, Invitation.class))
                .toList().contains(inv));
    }

}
