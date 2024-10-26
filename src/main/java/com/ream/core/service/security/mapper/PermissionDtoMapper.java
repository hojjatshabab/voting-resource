package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.Permission;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.PermissionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PermissionDtoMapper extends BaseMapper<PermissionDto, Permission> {
}
