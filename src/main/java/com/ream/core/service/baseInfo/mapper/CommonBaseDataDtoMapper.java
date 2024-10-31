package com.ream.core.service.baseInfo.mapper;

import com.ream.core.domain.baseInfo.CommonBaseData;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.CommonBaseDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommonBaseDataDtoMapper extends BaseMapper<CommonBaseDataDto, CommonBaseData> {

    @Override
    @Mapping(source = "commonBaseTypeId", target = "commonBaseType.id")
    @Mapping(source = "commonBaseTypeName", target = "commonBaseType.title")
    CommonBaseData toEntity(CommonBaseDataDto dto);

    @Override
    @Mapping(source = "commonBaseType.id", target = "commonBaseTypeId")
    @Mapping(source = "commonBaseType.title", target = "commonBaseTypeName")
    CommonBaseDataDto toDto(CommonBaseData entity);

}
