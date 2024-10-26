package com.ream.core.service.voting.mapper;


import com.ream.core.domain.voting.SmsLog;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.SmsLogDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SmsLogDtoMapper extends BaseMapper<SmsLogDto, SmsLog> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SmsLog toEntity(SmsLogDto dto);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SmsLogDto toDto(SmsLog entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<SmsLog> toEntityList(List<SmsLogDto> dtoList);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<SmsLogDto> toDtoList(List<SmsLog> entityList);


}