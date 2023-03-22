package com.jnz.teamManager.repository;

import com.jnz.teamManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
