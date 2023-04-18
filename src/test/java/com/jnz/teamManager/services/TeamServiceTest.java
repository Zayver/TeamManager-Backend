package com.jnz.teamManager.services;

import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.TeamNotExistsException;
import com.jnz.teamManager.repository.TeamRepository;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TeamServiceTest {

    private Team team;

    @Autowired
    private TeamRepository teamRepository;


    @BeforeAll
    void init(){
        this.team = Team.builder().name("tester_equipo")
                .description("descripción de prueba")
                .build();
    }
    @Test
    void addTeam(){
        val t = teamRepository.save(team);
        teamRepository.delete(t);
        assertTrue(t.getId() != 0);
    }


}
