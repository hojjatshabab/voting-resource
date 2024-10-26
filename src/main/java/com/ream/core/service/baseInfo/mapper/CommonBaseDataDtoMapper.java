package com.ream.core.service.baseInfo.mapper;

import com.ream.core.domain.baseInfo.Data;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.DataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommonBaseDataDtoMapper extends BaseMapper<DataDto, Data> {

    @Override
    @Mapping(source = "commonBaseTypeId", target = "commonBaseType.id")
    @Mapping(source = "commonBaseTypeName", target = "commonBaseType.title")
    Data toEntity(DataDto dto);

    @Override
    @Mapping(source = "commonBaseType.id", target = "commonBaseTypeId")
    @Mapping(source = "commonBaseType.title", target = "commonBaseTypeName")
    DataDto toDto(Data entity);

}
