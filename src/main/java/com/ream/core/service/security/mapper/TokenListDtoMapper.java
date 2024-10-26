package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.TokenList;
import com.ream.core.domain.security.User;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.TokenListDto;
import com.ream.core.service.security.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TokenListDtoMapper  extends BaseMapper<TokenListDto, TokenList> {

    @AfterMapping
    default void convertToNull(@MappingTarget TokenListDto tokenListDto) {
        tokenListDto.setToken(null);

    }
}
