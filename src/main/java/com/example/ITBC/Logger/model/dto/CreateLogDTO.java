package com.example.ITBC.Logger.model.dto;

import com.example.ITBC.Logger.model.LogType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CreateLogDTO {

    private String message;
    private LogType logType;
}
