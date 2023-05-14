package com.jnz.teamManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    private Long id;
    private UserDTO user;
    private TeamDTO team;
    private String message;
}
