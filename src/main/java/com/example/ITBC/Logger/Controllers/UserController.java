package com.example.ITBC.Logger.Controllers;

import com.example.ITBC.Logger.Model.dto.CreateLogDTO;
import com.example.ITBC.Logger.Model.mapper.UserMapper;
import com.example.ITBC.Logger.Model.dto.UserSingInDTO;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.Repository.UserRepository;
import com.example.ITBC.Logger.Security.TokenDTO;
import com.example.ITBC.Logger.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserSingUpDTO user) {
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> allUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserSingInDTO singInDTO) {
        String jwt = userService.getToken(singInDTO.getUsername(), singInDTO.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createLog(@RequestBody CreateLogDTO dto) {
        userService.createLog(dto);
        return ResponseEntity.ok(dto.getMassage());
    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }
}
