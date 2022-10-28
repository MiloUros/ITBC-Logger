package com.example.ITBC.Logger.model.mapper;

import com.example.ITBC.Logger.model.Log;
import com.example.ITBC.Logger.model.dto.CreateLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class LogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract Log createLogDtoToEntity(CreateLogDTO createLogDTO);
}
