package com.jnz.teamManager.dto;

import lombok.Data;

@Data
public class RequestDTO {
    private Long id;
    private UserDTO user;
    private TeamDTO team;
    private String message;
}
