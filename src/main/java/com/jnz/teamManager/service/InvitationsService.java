package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.exception.error.InvitationAlreadyExistsException;
import com.jnz.teamManager.repository.InvitationsRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvitationsService {
    @Autowired
    InvitationsRepository invitationsRepository;

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    public void addInvitation(Map<String, String> invitation){
        val user = userService.getUserById(Long.parseLong(invitation.get("userReceiverId")));
        val userOwner = userService.getUserById(Long.parseLong(invitation.get("userOwnerId")));
        val team = teamService.getTeamById(Long.parseLong(invitation.get("teamId")));

        if(this.invitationsRepository
                .findAll()
                .stream()
                .anyMatch(invitation1 -> invitation1.getUser().getId().equals(user.getId()) &&
                        invitation1.getTeamId().getId().equals(team.getId()))){
            throw new InvitationAlreadyExistsException();
        }

        val message = invitation.get("message");
        var invitationT = new Invitation();
        invitationT.setMessage(message);
        invitationT.setUser(user);
        invitationT.setUserOwner(userOwner);
        invitationT.setTeamId(team);
        this.invitationsRepository.save(invitationT);
    }

    public void acceptInvitation(Invitation invitation){
        userService.addTeamToUser(invitation.getUser().getId(), invitation.getTeamId().getId());
        deleteInvitation(invitation);
    }

    public void deleteInvitation(Invitation invitation){
        invitationsRepository.delete(invitation);
    }

    public Iterable<Invitation> getInvitationsById(Long id){
        return invitationsRepository.findAll().stream()
                .filter(invitation -> invitation.getUser().getId().equals(id)).collect(Collectors.toSet());
    }


}
