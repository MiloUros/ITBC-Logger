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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Create user.")
    @ApiResponse(responseCode = "201", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfoDTO.class))
    })
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody UserSingUpDTO user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToUserInfoDTO(userService.createUser(user)));
    }

    @Operation(summary = "Obtain access token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid username or password!")
    })
    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenDTO> loginUser(@RequestBody UserSingInDTO singInDTO) {
        String jwt = userService.getToken(singInDTO.getUsername(), singInDTO.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @Operation(summary = "Create log.", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log created", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> createLog(@RequestBody CreateLogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var log = logMapper.createLogDtoToEntity(dto);
        userService.createLog(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getMessage());
    }

    @Operation(summary = "Get list of all users.", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfoDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Change user password with provided id", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
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
