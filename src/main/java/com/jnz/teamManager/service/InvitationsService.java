package com.jnz.teamManager.service;

import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.repository.InvitationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationsService {
    @Autowired
    InvitationsRepository invitationsRepository;

    public Invitation addInvitation(Invitation invitation){
        return invitationsRepository.save(invitation);
    }

    public void deleteInvitation(Invitation invitation){
        invitationsRepository.delete(invitation);
    }

    public List<Invitation> getInvitationsById(Long id){
        return invitationsRepository.getInvitationsById(id);
    }


}
