package com.jnz.teamManager.dto;

import lombok.Data;

@Data
public class InvitationDTO {
    private Long id;
    private UserDTO user;
    private UserDTO userOwner;
    private TeamDTO teamId;
    private String message;
}
