package com.ream.core.service.baseInfo.mapper;

import com.ream.core.domain.baseInfo.CommonBaseType;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.CommonBaseTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommonBaseTypeDtoMapper extends BaseMapper<CommonBaseTypeDto, CommonBaseType> {
}
