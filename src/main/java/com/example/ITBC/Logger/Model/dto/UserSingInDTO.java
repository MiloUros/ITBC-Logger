package com.example.ITBC.Logger.Model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserSingInDTO {

    private String username;
    private String password;
}
