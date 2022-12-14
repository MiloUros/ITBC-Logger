package com.example.ITBC.Logger.repository;

import com.example.ITBC.Logger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAll();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
