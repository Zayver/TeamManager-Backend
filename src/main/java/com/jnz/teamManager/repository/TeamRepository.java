package com.jnz.teamManager.repository;

import com.jnz.teamManager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
