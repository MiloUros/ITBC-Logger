package com.example.ITBC.Logger.Model.mapper;

import com.example.ITBC.Logger.Model.Log;
import com.example.ITBC.Logger.Model.dto.CreateLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class LogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract Log createLogDtoToEntity(CreateLogDTO createLogDTO);
}
