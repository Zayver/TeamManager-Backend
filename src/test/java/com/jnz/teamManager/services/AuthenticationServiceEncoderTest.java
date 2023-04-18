package com.jnz.teamManager.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthenticationServiceEncoderTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoder() {
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        Assertions.assertNotEquals(password, encodedPassword);
        Assertions.assertTrue(passwordEncoder.matches(password, encodedPassword));
    }

}
