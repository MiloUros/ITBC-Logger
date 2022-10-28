package com.example.ITBC.Logger.bootstrap;

import com.example.ITBC.Logger.model.User;
import com.example.ITBC.Logger.model.UserRoles;
import com.example.ITBC.Logger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BootStrap implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        User admin = new User(null, "AdminUser", "admin@gmail.com", passwordEncoder.encode("Gaderene1"), UserRoles.ADMIN, new ArrayList<>());
        userRepository.save(admin);
    }
}
