package com.jnz.teamManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;


    @ManyToMany(mappedBy = "userTeams")
    Set<User> players;

    @OneToMany(cascade = CascadeType.ALL)
    Set<Request> requests;


}
