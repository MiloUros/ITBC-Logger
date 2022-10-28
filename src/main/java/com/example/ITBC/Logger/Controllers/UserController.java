package com.example.ITBC.Logger.Controllers;

import com.example.ITBC.Logger.Model.dto.CreateLogDTO;
import com.example.ITBC.Logger.Model.mapper.LogMapper;
import com.example.ITBC.Logger.Model.dto.UserSingInDTO;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.Security.TokenDTO;
import com.example.ITBC.Logger.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.SecurityContext;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LogMapper logMapper;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserSingUpDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserSingInDTO singInDTO) {
        String jwt = userService.getToken(singInDTO.getUsername(), singInDTO.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createLog(@RequestBody CreateLogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var log = logMapper.createLogDtoToEntity(dto);
        userService.createLog(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getMessage());
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/reset-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody String password, @PathVariable(name = "id") Long id) {
        userService.changePassword(id, password);
        return ResponseEntity.ok("Changed");
    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }
}
