package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.Role;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LogHistoryDtoMapper extends BaseMapper<LogHistoryDto, LogHistory> {
}
