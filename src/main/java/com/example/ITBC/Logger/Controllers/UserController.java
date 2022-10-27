package com.example.ITBC.Logger.Controllers;

import com.example.ITBC.Logger.Model.UserMapper;
import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Model.dto.UserSingInDTO;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.Security.PasswordValidation;
import com.example.ITBC.Logger.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserSingUpDTO user) {
        if (!userService.validatePassword(user.getPassword()).isValid()) {
            return ResponseEntity.badRequest().body(PasswordValidation.validator.getMessages(userService.validatePassword(user.getPassword())).toString());
        }
        if (userService.existByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists!");
        }
        if (userService.existByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email!");
        }
        if (user.getUsername().length() < 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must be more than 3 characters!");
        }
        return ResponseEntity.ok().body(userService.createUser(user));
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody UserSingInDTO singInDTO) {
//        User user = mapper.userSignInDto(singInDTO);
//    }
}
