package com.example.ITBC.Logger.model.mapper;

import com.example.ITBC.Logger.model.User;
import com.example.ITBC.Logger.model.dto.UserInfoDTO;
import com.example.ITBC.Logger.model.dto.UserSingInDTO;
import com.example.ITBC.Logger.model.dto.UserSingUpDTO;
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
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "userLogs", ignore = true)
    public abstract User userSignInDto(UserSingInDTO dto);

    public abstract UserInfoDTO userToUserInfoDTO(User user);
}
