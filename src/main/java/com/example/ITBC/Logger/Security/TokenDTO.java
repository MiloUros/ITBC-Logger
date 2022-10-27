package com.example.ITBC.Logger.Security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

//    @ApiModelProperty(value = "JWT for authenticated user.")
    private String accessToken;
}
