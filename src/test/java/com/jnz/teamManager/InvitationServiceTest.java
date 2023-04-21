package com.jnz.teamManager;

import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.entity.Team;
import com.jnz.teamManager.entity.User;
import com.jnz.teamManager.exception.error.InvitationAlreadyExistsException;
import com.jnz.teamManager.repository.InvitationsRepository;
import com.jnz.teamManager.service.InvitationsService;
import com.jnz.teamManager.service.TeamService;
import com.jnz.teamManager.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyLong;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InvitationServiceTest {

    @Mock
    private InvitationsRepository invitationsRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private TeamService teamService;

    private InvitationsService invitationsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        invitationsService = new InvitationsService();
        invitationsService.invitationsRepository = invitationsRepository;
        invitationsService.userService = userService;
        invitationsService.teamService = teamService;
    }

    @Test
    public void testAddInvitation() {
        // Mock de datos de entrada
        Map<String, String> invitation = new HashMap<>();
        invitation.put("userReceiverId", "1");
        invitation.put("userOwnerId", "2");
        invitation.put("teamId", "3");
        invitation.put("message", "Test invitation");

        // Mock de servicio de usuario
        when(userService.getUserById(1L)).thenReturn(new User());
        when(userService.getUserById(2L)).thenReturn(new User());

        // Mock de servicio de equipo
        when(teamService.getTeamById(3L)).thenReturn(new Team());

        // Mock de repositorio de invitaciones
        when(invitationsRepository.findAll()).thenReturn(Collections.emptyList());

        // Ejecución de método a probar
        invitationsService.addInvitation(invitation);

        // Verificación de llamadas a métodos
        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).getUserById(2L);
        verify(teamService, times(1)).getTeamById(3L);
        verify(invitationsRepository, times(1)).save(any(Invitation.class));
    }

    @Test
    public void testAddInvitation_DuplicateInvitation() {
        // Mock de datos de entrada
        Map<String, String> invitation = new HashMap<>();
        invitation.put("userReceiverId", "1");
        invitation.put("userOwnerId", "2");
        invitation.put("teamId", "3");
        invitation.put("message", "Test invitation");

        // Mock de servicio de usuario
        when(userService.getUserById(1L)).thenReturn(new User());
        when(userService.getUserById(2L)).thenReturn(new User());

        // Mock de servicio de equipo
        when(teamService.getTeamById(3L)).thenReturn(new Team());

        // Mock de repositorio de invitaciones con una invitación existente
        Invitation existingInvitation = new Invitation();
        existingInvitation.setUser(new User());
        existingInvitation.setTeamId(new Team());
        when(invitationsRepository.findAll()).thenReturn(Collections.singletonList(existingInvitation));

        // Ejecución de método a probar y verificación de excepción
        assertThrows(InvitationAlreadyExistsException.class, () -> invitationsService.addInvitation(invitation));

        // Verificación de llamadas a métodos
        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).getUserById(2L);
        verify(teamService, times(1)).getTeamById(3L);
        verify(invitationsRepository, never()).save(any(Invitation.class));
        }

    @Test
    public void testDeleteInvitation() {
        // Mock de datos de entrada
        Invitation invitation = new Invitation();
        invitation.setUser(new User());
        invitation.setTeamId(new Team());

        // Ejecución de método a probar
        invitationsService.deleteInvitation(invitation);

        // Verificación de llamadas a métodos
        verify(invitationsRepository, times(1)).delete(any(Invitation.class));
    }

  

}