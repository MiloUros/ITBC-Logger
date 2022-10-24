package com.example.ITBC.Logger.Repository;

import com.example.ITBC.Logger.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}
