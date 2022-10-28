package com.example.ITBC.Logger.Services;

import com.example.ITBC.Logger.Exceptions.*;
import com.example.ITBC.Logger.Model.Log;
import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Model.dto.CreateLogDTO;
import com.example.ITBC.Logger.Model.mapper.LogMapper;
import com.example.ITBC.Logger.Model.mapper.UserMapper;
import com.example.ITBC.Logger.Model.UserRoles;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.Repository.LogRepository;
import com.example.ITBC.Logger.Repository.UserRepository;
import com.example.ITBC.Logger.Security.PasswordValidation;
import com.example.ITBC.Logger.Security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordValidation passwordValidation;
    private final TokenProvider tokenProvider;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final LogMapper logMapper;
    private final LogRepository logRepository;

    private static final List<String> DEFAULT_ROLES = List.of("USER");
    private static final List<String> ADMIN_ROLES = List.of("ADMIN");


    public User createUser(UserSingUpDTO user) {
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new InvalidEmailException("Email not valid");
        }
        if (!passwordValidation.validatePassword(user.getPassword()).isValid()) {
            throw new InvalidPasswordException("Password not valid");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameTakenException("Username is taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailTakenException("Email is taken");
        }
        var userEntity = userRepository.save(mapper.userSignUpDtoToEntity(user));
        userEntity.setRole(UserRoles.USER);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public String getToken(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException("Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid username or password");
        }
        if (user.getRole().toString().equals("ADMIN")) {
            return tokenProvider.create(user, ADMIN_ROLES);
        }
        return tokenProvider.create(user, DEFAULT_ROLES);
    }


    public void createLog(CreateLogDTO createLogDTO, String username) {
        Log log = logMapper.createLogDtoToEntity(createLogDTO);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (log.getMessage().length() > 1024) {
            throw new InvalidCredentialsException("Message must be between 0 and 1024 characters");
        }
        log.setCreatedDate(Date.from(Instant.now()));
        log.setUser(user);
        logRepository.save(log);
    }

    public void changePassword(Long id, String password) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        user.setPassword(passwordEncoder.encode(password));
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }
}
