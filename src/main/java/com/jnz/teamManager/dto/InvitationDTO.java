package com.jnz.teamManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDTO {
    private Long id;
    private UserDTO user;
    private UserDTO userOwner;
    private TeamDTO teamId;
    private String message;
}
