package com.jnz.teamManager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnz.teamManager.dto.InvitationDTO;
import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.service.InvitationsService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class InvitationControllerTest {

    @MockBean
    private InvitationsService invitationsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getInvitation() throws Exception {
        val invitations = List.of(
                InvitationDTO.builder().id(1L).message("Hey").build(),
                InvitationDTO.builder().id(2L).message("Hello").build()
        );
        Mockito.when(invitationsService.getInvitationsById(1L)).thenReturn(invitations);

        val res = mockMvc.perform(get("/invitation/get").param("id", "1")).andExpect(status().isOk()).andReturn();
        val response = objectMapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<InvitationDTO>>() {});

        assertIterableEquals(response, invitations);
    }


    @Test
    void getInvitationsBadReques() throws Exception {
        mockMvc.perform(get("/invitation/get")).andExpect(status().isBadRequest());
    }

    @Test
    void addInvitation() throws Exception{
        val body = Map.of(
                "userReceiverId", 1L,
                "userOwnerId", 2L,
                "teamId", 1L
        );
        mockMvc.perform(post("/invitation/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body))).andExpect(status().isOk());
    }

    @Test
    void addInvitationBadRequest() throws Exception {
        mockMvc.perform(post("/invitation/add")).andExpect(status().isBadRequest());
    }

    @Test
    void acceptInvitation() throws Exception {
        val invitation = Invitation.builder().message("Hey here is a invitation Message").build();
        mockMvc.perform(post("/invitation/accept").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invitation))).andExpect(status().isOk());
    }

    @Test
    void acceptInvitationBadRequest() throws Exception {
        mockMvc.perform(post("/invitation/accept")).andExpect(status().isBadRequest());
    }

    @Test
    void declineInvitation() throws Exception{
        val invitation = Invitation.builder().message("Hey here is a invitation Message").build();
        mockMvc.perform(post("/invitation/decline").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invitation))).andExpect(status().isOk());
    }

    @Test
    void declineInvitationBadRequest() throws Exception {
        mockMvc.perform(post("/invitation/decline")).andExpect(status().isBadRequest());
    }



}
