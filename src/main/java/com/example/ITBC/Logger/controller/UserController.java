package com.example.ITBC.Logger.controller;

import com.example.ITBC.Logger.logSpec.LogSpec;
import com.example.ITBC.Logger.model.Log;
import com.example.ITBC.Logger.model.LogType;
import com.example.ITBC.Logger.model.dto.CreateLogDTO;
import com.example.ITBC.Logger.model.dto.UserInfoDTO;
import com.example.ITBC.Logger.model.mapper.LogMapper;
import com.example.ITBC.Logger.model.dto.UserSingInDTO;
import com.example.ITBC.Logger.model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.model.mapper.UserMapper;
import com.example.ITBC.Logger.repository.LogRepository;
import com.example.ITBC.Logger.security.TokenDTO;
import com.example.ITBC.Logger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LogMapper logMapper;
    private final UserMapper userMapper;
    private final LogRepository logRepository;

    @PostMapping("/register")
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody UserSingUpDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToUserInfoDTO(userService.createUser(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody UserSingInDTO singInDTO) {
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
    public ResponseEntity<List<UserInfoDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/reset-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody String password, @PathVariable(name = "id") Long id) {
        userService.changePassword(id, password);
        return ResponseEntity.ok("Changed");
    }

    //TODO: Implement search endpoint for logs
//    @GetMapping("/search")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<List<Log>> search(@RequestParam(value = "message", required = false) String message,
//                                            @RequestParam(value = "logType", required = false) LogType logType,
//                                            @RequestParam(value = "startDate", required = false) Date startDate,
//                                            @RequestParam(value = "endDate", required = false) Date endDate) {
//        Specification<Log> specification = LogSpec.getSpec(message, logType, startDate, endDate);
//        return ResponseEntity.ok(logRepository.findAll(specification));
//    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }
}
