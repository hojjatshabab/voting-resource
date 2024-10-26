package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.RefreshToken;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.RefreshTokenDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RefreshTokenDtoMapper extends BaseMapper<RefreshTokenDto, RefreshToken> {
}
