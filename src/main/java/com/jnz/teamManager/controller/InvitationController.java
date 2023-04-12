package com.jnz.teamManager.controller;

import com.jnz.teamManager.entity.Invitation;
import com.jnz.teamManager.service.InvitationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    InvitationsService invitationsService;
    @GetMapping("/get/{id}")
    public Iterable<Invitation> getInvitation(@PathVariable("id") Long id){
        return invitationsService.getInvitationsById(id);
    }
    @PostMapping("/add")
    public void addInvitation(@RequestBody Map<String, String> request){
        invitationsService.addInvitation(request);
    }

    @PostMapping("/accept")
    public void acceptInvitation(@RequestBody Invitation invitation){
        invitationsService.acceptInvitation(invitation);
    }
    @PostMapping("/decline")
    public void declineInvitation(@RequestBody Invitation invitation){
        invitationsService.deleteInvitation(invitation);
    }
}
