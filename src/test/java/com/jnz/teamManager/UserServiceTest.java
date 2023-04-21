package com.jnz.teamManager;

import com.jnz.teamManager.auth.dto.AuthenticationRequest;
import com.jnz.teamManager.auth.dto.RegisterRequest;
import com.jnz.teamManager.entity.Role;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.EmailAlreadyExistsException;
import com.jnz.teamManager.exception.error.UserNotExistsException;
import com.jnz.teamManager.exception.error.UsernameAlreadyExistsException;
import com.jnz.teamManager.repository.UserRepository;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;
import com.jnz.teamManager.service.auth.AuthenticationService;
import com.jnz.teamManager.service.auth.JwtService;
import lombok.val;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .username("test")
                .password("test")
                .role(Role.USER)
                .build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteById(user.getId());
    }

    @Test
    public void shouldGetUserById() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void shouldThrowUserNotExistsException() {
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotExistsException.class, () -> userService.getUserById(2L));
    }

    @Test
    public void shouldAddUser() {
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        User result = userService.addUser(user);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void shouldThrowUsernameAlreadyExistsException() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        User newUser = User.builder()
                .email("test2@test.com")
                .username("test")
                .password("test")
                .role(Role.USER)
                .build();
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> userService.addUser(newUser));
    }

    @Test
    public void shouldThrowEmailAlreadyExistsException() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        User newUser = User.builder()
                .email("test@test.com")
                .username("test2")
                .password("test")
                .role(Role.USER)
                .build();
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> userService.addUser(newUser));
    }

    @Test
    void shouldDeleteUser() {
        // Arrange
        User user = User.builder()
                .email("user@example.com")
                .username("user1")
                .password("password")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        Long userId = user.getId();

        // Act
        userService.deleteUser(userId);

        // Assert
        assertThrows(UserNotExistsException.class, () -> userService.getUserById(userId));
    }
}