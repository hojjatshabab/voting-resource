package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.User;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import com.ream.core.service.security.dto.UserDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserDtoMapper extends BaseMapper<UserDto, User> {


    @AfterMapping
    default void convertToNull(@MappingTarget UserDto userDto) {
        userDto.setPassword(null);


    }

}
