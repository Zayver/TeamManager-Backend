package com.jnz.teamManager.services;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.UserNotExistsException;
import com.jnz.teamManager.repository.UserRepository;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    private TestEntityManager entityManager;


    @BeforeAll
    public void setUp() {
        val tester =
                User.builder()
                        .email("tester@correo.com")
                        .password("123456")
                        .username("tester")
                        .build()
                ;

        Mockito.when(userRepository.findAll()).thenReturn(
                List.of(tester)
        );
    }

    @Test
    void getUsernameThatExists(){
        val user = userService.getUserByUsername("tester");
        assertEquals("tester", user.getUsername());
    }

    @Test
    void getUserThatNotExists(){
        try{
            val user = userService.getUserByUsername("hola");
        }catch (UserNotExistsException e){
            return;
        }
        fail();
    }

    @Test
    void saveUser(){
        val user = User.builder()
                .email("tester@correo.com")
                .username("tester")
                .password("123456")
                .build();
        this.userService.addUser(user);
    }



}
