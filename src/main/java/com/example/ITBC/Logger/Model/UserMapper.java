package com.example.ITBC.Logger.Model;

import com.example.ITBC.Logger.Model.User;
import com.example.ITBC.Logger.Model.UserRoles;
import com.example.ITBC.Logger.Model.dto.UserSingInDTO;
import com.example.ITBC.Logger.Model.dto.UserSingUpDTO;
import lombok.AccessLevel;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class UserMapper {
    @Autowired
    @Setter(AccessLevel.PACKAGE)
    protected PasswordEncoder passwordEncoder;



    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "userLogs", ignore = true)
    public abstract User userSignUpDtoToEntity(UserSingUpDTO dto);

    @Mapping(target = "id", ignore = true)
    public User userSignInDto(UserSingInDTO dto){
        return User.builder().username(dto.getUsername()).password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRoles.USER).build();
    }
}