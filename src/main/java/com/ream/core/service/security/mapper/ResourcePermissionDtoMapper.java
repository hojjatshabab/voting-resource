package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.ResourcePermission;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.ResourcePermissionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ResourcePermissionDtoMapper extends BaseMapper<ResourcePermissionDto, ResourcePermission> {


    @Override
    @Mapping(source = "permissionId", target = "permission.id")
    @Mapping(source = "resourceId", target = "resource.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResourcePermission toEntity(ResourcePermissionDto dto);

    @Override
    @Mapping(source = "permission.id", target = "permissionId")
    @Mapping(source = "resource.id", target = "resourceId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResourcePermissionDto toDto(ResourcePermission entity);

    @Override
    @Mapping(source = "permissionId", target = "permission.id")
    @Mapping(source = "resourceId", target = "resource.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<ResourcePermission> toEntityList(List<ResourcePermissionDto> dtoList);

    @Override
    @Mapping(source = "permission.id", target = "permissionId")
    @Mapping(source = "resource.id", target = "resourceId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<ResourcePermissionDto> toDtoList(List<ResourcePermission> entityList);
}
