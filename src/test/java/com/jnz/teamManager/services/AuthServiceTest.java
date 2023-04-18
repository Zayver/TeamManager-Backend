package com.jnz.teamManager.services;

import com.jnz.teamManager.auth.dto.AuthenticationRequest;
import com.jnz.teamManager.auth.dto.AuthenticationResponse;
import com.jnz.teamManager.auth.dto.RegisterRequest;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.auth.AuthenticationService;
import lombok.val;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    private RegisterRequest request;

    private AuthenticationResponse user;

    @BeforeAll
    void init(){
        request = RegisterRequest.builder().
                email("tester_test@correo.com")
                .username("tester_test")
                .password("123456")
                .build()
                ;
    }

    @Test
    void register(){
        try{
            user = this.authenticationService.register(request);
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void authenticate(){
        AuthenticationResponse authenticationResponse = null;
        val r = AuthenticationRequest.builder().username("tester_test")
                .password("123456").build();
        try{
            authenticationResponse = this.authenticationService.authenticate(r);
        }catch (AuthenticationException e){
            fail();
        }
    }

    @Test
    void authenticateWithError(){
        AuthenticationResponse authenticationResponse = null;
        val r = AuthenticationRequest.builder().username("tester_test")
                .password("sdkhjfksdjfjkhsdfjk").build();
        try{
            this.authenticationService.authenticate(r);
        }catch (AuthenticationException e){
            //Success
        }
    }


    @AfterAll
    void end(){
        userService.deleteUser(user.getId());
    }
}
