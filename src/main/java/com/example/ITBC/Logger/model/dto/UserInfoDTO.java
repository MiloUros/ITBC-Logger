package com.example.ITBC.Logger.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserInfoDTO {
    String username;
    String email;
}
