package com.seguranca_urbana.backend.repositorys;

import com.seguranca_urbana.backend.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}