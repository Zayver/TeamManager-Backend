package com.jnz.teamManager.services;

import com.jnz.teamManager.auth.dto.AuthenticationRequest;
import com.jnz.teamManager.auth.dto.RegisterRequest;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.repository.UserRepository;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.auth.AuthenticationService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;


    @Test
    void register(){
        val user = User.builder().id(1L).username("tester").email("tester@correo.com").password("1234").build();
        val regRequest = RegisterRequest.builder()
                        .email("tester@correo.com").password("1234").username("tester").build();
        Mockito.when(userService.addUser(Mockito.any())).thenReturn(user);
        val res = authenticationService.register(regRequest);

        assertNotNull(res.getToken());
        assertEquals(res.getUsername(), user.getUsername());
    }

    @Test
    void authenticate(){
        val user = User.builder().id(1L).username("admin").email("admin@correo.com").build();
        val authReq = AuthenticationRequest.builder().username("admin").password("1234").build();
        Mockito.when(userService.getUserByUsername(Mockito.any())).thenReturn(user);
        val res = authenticationService.authenticate(authReq);
        assertNotNull(res.getToken());
        assertEquals(res.getUsername(), user.getUsername());
    }


}
