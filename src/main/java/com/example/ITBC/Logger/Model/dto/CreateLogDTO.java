package com.example.ITBC.Logger.Model.dto;

import com.example.ITBC.Logger.Model.LogType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class CreateLogDTO {

    private String message;
    private LogType logType;
}
