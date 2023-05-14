package com.jnz.teamManager.services;

import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.RequestAlreadyExistsException;
import com.jnz.teamManager.repository.RequestRepository;
import com.jnz.teamManager.service.RequestService;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.TeamService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
public class RequestServiceTest {

    @MockBean
    private RequestRepository requestRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private TeamService teamService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void addRequest(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();

        //To mock a protected method
        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", anyLong())).thenReturn(user);
        Mockito.when(ReflectionTestUtils.invokeMethod(teamService, "getTeamById", anyLong())).thenReturn(team);


        requestService.addRequest(Map.of(
                "id", "1",
                "teamId", "1"
        ));

        Mockito.verify(requestRepository).save(Mockito.any());
    }

    @Test
    void addRequest_AlreadyExists(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val requests = List.of(
                Request.builder().team(team).user(user).build()
        );

        Mockito.when(requestRepository.findAll()).thenReturn(requests);
        Mockito.when(ReflectionTestUtils.invokeMethod(userService, "getUserById", anyLong())).thenReturn(user);
        Mockito.when(ReflectionTestUtils.invokeMethod(teamService, "getTeamById", anyLong())).thenReturn(team);

        try{
            requestService.addRequest(Map.of(
                    "id", "1",
                    "teamId", "1"
            ));
        }catch (RequestAlreadyExistsException e){
            return;
        }
        fail();
    }

    @Test
    void deleteRequest(){
        val user = User.builder().id(1L).username("tester").build();
        val team = Team.builder().id(1L).name("Test team").build();
        val request = Request.builder().team(team).user(user).build();

        val requests = List.of(
                request
        );
        Mockito.when(requestRepository.findAll()).thenReturn(requests);
        requestService.deleteRequest(request);
        Mockito.verify(requestRepository).delete(request);
    }

    @Test
    void getRequestByTeamId(){
        val team = Team.builder().id(1L).name("Test team").build();
        val team2 = Team.builder().id(2L).name("Test team Two").build();
        val requests = List.of(
                Request.builder().team(team2).build(),
                Request.builder().team(team).build()

        );
        Mockito.when(requestRepository.findAll()).thenReturn(requests);

        val res = requestService.getRequestByTeamId(1L);
        assertEquals(StreamSupport.stream(res.spliterator(), false)
                .toList().stream().findFirst().get().getTeam(), modelMapper.map(team, TeamDTO.class));

    }

    @Test
    void acceptRequest(){
        Mockito.doNothing().when(userService).addTeamToUser(Mockito.anyLong(), Mockito.anyLong());
        requestService.acceptRequest(Request.builder().user(User.builder().id(1L).build()).team(Team.builder().id(1L).build()).build());
        Mockito.verify(requestRepository).delete(Mockito.any());
    }

}
