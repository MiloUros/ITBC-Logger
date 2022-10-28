package com.example.ITBC.Logger.Bootstrap;

import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Model.UserRoles;
import com.example.ITBC.Logger.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        User admin = new User(null, "AdminUser", "admin@gmail.com", passwordEncoder.encode("Gaderene1"), UserRoles.ADMIN, null );
        userRepository.save(admin);
    }
}
