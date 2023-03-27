package com.jnz.teamManager.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "team")
@EqualsAndHashCode(exclude = {"players"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;


    String description;


    @ManyToMany(mappedBy = "userTeams")
    @JsonIgnore
    Set<User> players;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Request> requests;



}
