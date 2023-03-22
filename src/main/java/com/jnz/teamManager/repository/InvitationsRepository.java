package com.jnz.teamManager.repository;

import com.jnz.teamManager.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvitationsRepository extends JpaRepository<Invitation, Long> {

    @Query(value = "SELECT * FROM invitation WHERE invitation.userId = id", nativeQuery = true)
    List<Invitation> getInvitationsById(@Param("id") Long id);
}
