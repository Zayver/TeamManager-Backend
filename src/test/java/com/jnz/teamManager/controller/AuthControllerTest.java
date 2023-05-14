package com.jnz.teamManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnz.teamManager.auth.dto.AuthenticationRequest;
import com.jnz.teamManager.auth.dto.RegisterRequest;
import com.jnz.teamManager.service.auth.AuthenticationService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        val registerRequest = RegisterRequest.builder().username("hey").password("1234").email("hey@email.com").build();
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest))).andExpect(status().isOk());
    }

    @Test
    void registerBadRequest() throws Exception {
        mockMvc.perform(post("/auth/register")).andExpect(status().isBadRequest());
    }

    @Test
    void authenticate() throws Exception {
        val authRequest = AuthenticationRequest.builder().username("hey").password("1234").build();
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest))).andExpect(status().isOk());
    }

    @Test
    void authenticateBadRequest() throws Exception {
        mockMvc.perform(post("/auth/authenticate")).andExpect(status().isBadRequest());
    }
}
