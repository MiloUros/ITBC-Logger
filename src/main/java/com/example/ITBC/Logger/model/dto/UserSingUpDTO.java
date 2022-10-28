package com.example.ITBC.Logger.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserSingUpDTO {

    @ApiModelProperty(value = "User's username.", example = "someuser123")
    private String username;

    @ApiModelProperty(value = "User's email.", example = "user@mail.com")
    private String email;

    @ApiModelProperty(value = "Plaintext password.", example = "p@ssw0rd")
    private String password;
}
