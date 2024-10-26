package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.BlackListToken;
import com.ream.core.domain.security.LogHistory;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.BlackListTokenDto;
import com.ream.core.service.security.dto.LogHistoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BlackListTokenDtoMapper extends BaseMapper<BlackListTokenDto, BlackListToken> {
}
