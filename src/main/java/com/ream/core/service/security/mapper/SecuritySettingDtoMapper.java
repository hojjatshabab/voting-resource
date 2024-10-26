package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.SecuritySetting;
import com.ream.core.domain.security.User;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.SecuritySettingDto;
import com.ream.core.service.security.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SecuritySettingDtoMapper extends BaseMapper<SecuritySettingDto, SecuritySetting> {
}
