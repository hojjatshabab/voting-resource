package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.Access;
import com.ream.core.domain.security.LogHistory;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.AccessDto;
import com.ream.core.service.security.dto.LogHistoryDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccessDtoMapper extends BaseMapper<AccessDto, Access> {


    @Override
    @Mapping(source = "resourcePermissionId", target = "resourcePermission.id")
    @Mapping(source = "roleId", target = "role.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Access toEntity(AccessDto dto);

    @Override
    @Mapping(source = "resourcePermission.id", target = "resourcePermissionId")
    @Mapping(source = "role.id", target = "roleId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccessDto toDto(Access entity);

    @Override
    @Mapping(source = "resourcePermissionId", target = "resourcePermission.id")
    @Mapping(source = "roleId", target = "role.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Access> toEntityList(List<AccessDto> dtoList);

    @Override
    @Mapping(source = "resourcePermission.id", target = "resourcePermissionId")
    @Mapping(source = "role.id", target = "roleId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<AccessDto> toDtoList(List<Access> entityList);
}
