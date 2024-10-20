package com.Hms.repository;

import com.Hms.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
  Optional<UserApp> findByUsername(String username);
  Optional<UserApp> findByEmail(String email);
}