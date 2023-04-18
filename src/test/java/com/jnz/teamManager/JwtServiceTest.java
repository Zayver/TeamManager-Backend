package com.jnz.teamManager;

import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.auth.JwtService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Test
    public void testGenerateAndValidateToken() {
        // Creamos un nuevo usuario
        User user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("password123")
                .build();
        userService.addUser(user);

        // Generamos un token para el usuario
        UserDetails userDetails = userService.getUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        // Verificamos si el token es válido
        boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
        Assertions.assertTrue(isTokenValid);
    }
}