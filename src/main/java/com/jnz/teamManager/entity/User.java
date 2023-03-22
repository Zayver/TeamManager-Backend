package com.jnz.teamManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String email;
    @Column(unique = true)
    String username;
    String password;

    @ManyToMany
    @JoinTable(
            name = "user_teams",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    Set<Team> userTeams;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<Invitation> invitations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userOwner")
    Set<Invitation> invitationsCreated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<Request> requestsCreated;



}
