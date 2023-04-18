package com.jnz.teamManager.services;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.auth.JwtService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;
    private User user;
    @BeforeAll
    void init(){
        user = User.builder().username("tester_test")
                .password("123456")
                .email("tester_test@test.com")
                .build();
    }


    @Test
    void genToken(){
        val token =  jwtService.generateToken(user);
        assertEquals(2, token.chars().filter(ch -> ch == '.').count());
    }

    @Test
    void checkValidToken(){
        val token = jwtService.generateToken(user);
        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void checkInvalidToken(){
        val token = "123456789";
        try{
            jwtService.isTokenValid(token, user);
        }catch (JwtException e){
            return;
        }
        fail();
    }

    @Test
    void checkUsernameToken(){
        val token = jwtService.generateToken(user);
        val username = jwtService.extractUsername(token);
        assertEquals(username, user.getUsername());
    }


}
