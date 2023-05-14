package com.jnz.teamManager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnz.teamManager.dto.TeamDTO;
import com.jnz.teamManager.dto.UserDTO;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.service.TeamService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TeamControllerTest {

    @MockBean
    private TeamService teamService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        val teams = List.of(
                TeamDTO.builder().name("Test team").build(),
                TeamDTO.builder().name("test team 2").build()
        );

        Mockito.when(teamService.getTeamsWhereUserIsNotAt(1L)).thenReturn(teams);

        val res = mockMvc.perform(get("/team/all").param("id", "1")).andExpect(status().isOk()).andReturn();
        val response = objectMapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<TeamDTO>>(){});

        assertIterableEquals(response, teams);
    }

    @Test
    void findAllBadRequest() throws Exception {
        mockMvc.perform(get("/team/all")).andExpect(status().isBadRequest());
    }

    @Test
    void addTeam() throws Exception {
        val team = Team.builder().id(1L).name("Tester team").description("description").build();
        mockMvc.perform(post("/team/add").param("id", "1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team))).andExpect(status().isOk());
    }

    @Test
    void addTeamBadRequest() throws Exception{
        val team = Team.builder().id(1L).name("Tester team").description("description").build();
        mockMvc.perform(post("/team/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team))).andExpect(status().isBadRequest());
        mockMvc.perform(post("/team/add").param("id", "1")).andExpect(status().isBadRequest());
        mockMvc.perform(post("/team/add")).andExpect(status().isBadRequest());
    }

    @Test
    void updateTeam() throws Exception {
        val team = Team.builder().id(1L).name("Tester team").description("description").build();
        mockMvc.perform(put("/team/update").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(team))).andExpect(status().isOk());
    }

    @Test
    void updateTeamBadRequest() throws Exception {
        mockMvc.perform(put("/team/update")).andExpect(status().isBadRequest());
    }
    @Test
    void deleteTeam() throws Exception {
        mockMvc.perform(delete("/team/delete").param("id", "1")).andExpect(status().isOk());
    }

    @Test
    void deleteTeamBadRequest() throws Exception {
        mockMvc.perform(delete("/team/delete")).andExpect(status().isBadRequest());
    }
    @Test
    void getUsersByTeamId() throws Exception {
        val users = List.of(
                UserDTO.builder().username("tester").email("tester@correo.com").id(2L).build(),
                UserDTO.builder().username("admin").email("admin@correo.com").id(3L).build()
        );
        Mockito.when(teamService.getUsersByTeamId(1L)).thenReturn(users);

        val res = mockMvc.perform(get("/team/users").param("id", "1")).andExpect(status().isOk()).andReturn();
        val response = objectMapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<UserDTO>>(){});

        assertIterableEquals(users, response);
    }

    @Test
    void getUsersByTeamIdBadRequest() throws Exception {
        mockMvc.perform(get("/team/users")).andExpect(status().isBadRequest());
    }
}
