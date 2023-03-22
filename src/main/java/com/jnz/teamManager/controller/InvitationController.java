package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.service.InvitationsService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    InvitationsService invitationsService;
    @GetMapping("/get/{id}")
    public List<Invitation> getInvitation(@RequestParam("id") Long id){
        return invitationsService.getInvitationsById(id);
    }
    @PostMapping("/add")
    public Invitation addInvitation(@RequestBody Invitation invitation){
        return invitationsService.addInvitation(invitation);
    }

    @PostMapping("/accept")
    public void acceptInvitation(@RequestBody Invitation invitation){
        //TODO

    }
    @PostMapping("/decline")
    public void declineInvitation(@RequestBody Invitation invitation){
        invitationsService.deleteInvitation(invitation);
    }
}
