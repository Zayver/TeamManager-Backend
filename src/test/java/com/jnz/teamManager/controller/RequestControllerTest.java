package com.jnz.teamManager.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnz.teamManager.dto.RequestDTO;
import com.jnz.teamManager.entity.Request;
import com.jnz.teamManager.service.RequestService;
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
public class RequestControllerTest {

    @MockBean
    private RequestService requestService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addRequest() throws Exception{
        val request = Map.of(
                "id", 1L,
                "teamId", 1L
        );

        mockMvc.perform(post("/request/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    void addRequestBadRequest() throws Exception {
        mockMvc.perform(post("/request/add")).andExpect(status().isBadRequest());
    }

    @Test
    void getRequest() throws Exception {
        val request = List.of(
                RequestDTO.builder().message("Heyy this is a request").build(),
                RequestDTO.builder().message("This is a second request").build()
        );

        Mockito.when(requestService.getRequestByTeamId(1L)).thenReturn(request);

        val res = mockMvc.perform(get("/request/get").param("id", "1")).andExpect(status().isOk()).andReturn();
        val response = objectMapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<RequestDTO>>() {
        });
        assertIterableEquals(response, request);
    }

    @Test
    void getRequestBadRequest() throws Exception {
        mockMvc.perform(get("/request/get")).andExpect(status().isBadRequest());
    }

    @Test
    void acceptRequest() throws Exception{
        val request = Request.builder().message("Hey this is a request").build();
        mockMvc.perform(post("/request/accept").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    void acceptRequestBadRequest() throws Exception {
        mockMvc.perform(post("/request/accept")).andExpect(status().isBadRequest());
    }

    @Test
    void declineRequest() throws Exception{
        val request = Request.builder().message("Hey this is a request").build();
        mockMvc.perform(post("/request/decline").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    void declineRequestBadRequest() throws Exception {
        mockMvc.perform(post("/request/decline")).andExpect(status().isBadRequest());
    }

}
