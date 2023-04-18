package com.jnz.teamManager.services;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.UserNotExistsException;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user;


    @BeforeAll
    void initialize(){
        user = User.builder().email("tester_test@correo.com")
                .username("tester_test")
                .password("123456")
                .build();
    }
    @Test
    void addUser(){
        val nUser = userService.addUser(user);
        assertTrue(nUser.getId() != 0);
    }

    @Test
    void getUserThatDontExist(){
        try{
            userService.getUserByUsername("jufaskhaksdfhkjsafdafsdsfdfsa");
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void deleteU(){
        val userN = User.builder().email("tester2_test@correo.com")
                .username("tester2_test")
                .password("123456")
                .build();
        val nUser = userService.addUser(userN);
        userService.deleteUser(nUser.getId());
        try{
            userService.getUserByUsername(userN.getUsername());
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @AfterEach
    void deleteUser(){
        try{
            userService.deleteUser(user.getId());
        }catch (Exception e){
        }
    }
}
