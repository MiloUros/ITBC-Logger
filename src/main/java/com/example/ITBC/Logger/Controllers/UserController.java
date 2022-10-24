package com.example.ITBC.Logger.Controllers;

import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Repository.UserRepository;
import com.example.ITBC.Logger.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.ITBC.Logger.UserService.validator;

@RestController
public class UserController {

    UserRepository userRepository;
    UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/api/clients/all")
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/api/clients/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (!userService.validPassword(user.getPassword()).isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.getMessages(userService.validPassword(user.getPassword())).toString());
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken!");
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email");
        }
        if (user.getUserName().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must be more than 3 characters!");
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Registered!");
    }
}
