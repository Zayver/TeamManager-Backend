package com.jnz.teamManager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.Role;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsersExceptCaller() throws Exception {
        val user = UserDTO.builder().username("prueba").email("prueba@correo.com").id(1L).build();
        var users = new ArrayList<>(List.of(
                user,
                UserDTO.builder().username("tester").email("tester@correo.com").id(2L).build(),
                UserDTO.builder().username("admin").email("admin@correo.com").id(3L).build()
        ));

        users.remove(0);

        Mockito.when(userService.getAllUsersExceptCaller(1L)).thenReturn(users);

        val res = mockMvc.perform(get("/user/all").param("id", "1")).andExpect(status().isOk()).andReturn();
        var responseUser =  objectMapper.readValue(res.getResponse().getContentAsString(), List.class);

        assertFalse(responseUser.contains(user));
    }

    @Test
    void getAllUsersExceptCallerBadRequest() throws Exception {
        mockMvc.perform(get("/user/all")).andExpect(status().isBadRequest());
    }

    @Test
    void getTeamsByUserId() throws Exception {
        val teams = List.of(
                TeamDTO.builder().name("Team Test").build(),
                TeamDTO.builder().name("Team tester").build()
        );
        Mockito.when(userService.getTeamsByUserId(1L)).thenReturn(teams);

        val res = mockMvc.perform(get("/user/teams").param("id", "1")).andExpect(status().isOk()).andReturn();
        List<TeamDTO> response = objectMapper.readValue(res.getResponse().getContentAsString(), new TypeReference<>() {});

        assertIterableEquals(teams, response);
    }

    @Test
    void getTeamsByUserIdBadRequest() throws Exception {
        mockMvc.perform(get("/user/teams")).andExpect(status().isBadRequest());
    }
    
}
