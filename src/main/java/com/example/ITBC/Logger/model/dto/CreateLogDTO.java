package com.example.ITBC.Logger.model.dto;

import com.example.ITBC.Logger.model.LogType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CreateLogDTO {

    @ApiModelProperty(value = "User's log message", example = "Some log message")
    private String message;

    @ApiModelProperty(value = "User's log type.", example = "Info, Warning and Error")
    private LogType logType;
}
