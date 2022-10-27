package com.example.ITBC.Logger.Services;

import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Model.UserMapper;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import com.example.ITBC.Logger.Repository.UserRepository;
import com.example.ITBC.Logger.Security.PasswordValidation;
import com.example.ITBC.Logger.Security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordValidation passwordValidation;
    private final TokenProvider tokenProvider;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private static final List<String> DEFAULT_ROLES = List.of("USER");
    private static final List<String> ADMIN_ROLES = List.of("ADMIN");


    public String createUser(UserSingUpDTO user) {
        var userEntity = userRepository.save(mapper.userSignUpDtoToEntity(user));
        return tokenProvider.create(userEntity, DEFAULT_ROLES);
    }

    @Transactional(readOnly = true)
    public String getToken(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(this::badCredentialsException);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw badCredentialsException();
        }
        if (user.getRole().toString().equals("ADMIN")) {
            return tokenProvider.create(user, ADMIN_ROLES);
        }
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public RuleResult validatePassword(String password) {
        return passwordValidation.validatePassword(password);
    }

    public Boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private BadCredentialsException badCredentialsException() {
        return new BadCredentialsException("Invalid username or password.");
    }

}
