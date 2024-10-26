package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.Role;
import com.ream.core.domain.security.User;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleDtoMapper extends BaseMapper<RoleDto, Role> {
}
