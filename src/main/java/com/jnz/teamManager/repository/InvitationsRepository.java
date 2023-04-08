package com.jnz.teamManager.repository;

import com.jnz.teamManager.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface InvitationsRepository extends JpaRepository<Invitation, Long> {
}
