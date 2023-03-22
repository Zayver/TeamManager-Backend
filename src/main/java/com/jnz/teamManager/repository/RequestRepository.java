package com.jnz.teamManager.repository;

import com.jnz.teamManager.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
