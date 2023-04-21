package com.jnz.teamManager;

import com.jnz.teamManager.auth.dto.RegisterRequest;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.auth.AuthenticationService;
import com.jnz.teamManager.service.auth.JwtService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void register_shouldCreateUser() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("testuser@test.com");
        registerRequest.setPassword("password123");

        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedpassword");

        // when
        authenticationService.register(registerRequest);

        // then
        verify(userService).addUser(any(User.class));
    }
}