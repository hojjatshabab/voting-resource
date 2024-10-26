package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.Resource;
import com.ream.core.domain.security.ResourcePermission;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.security.dto.ResourceDto;
import com.ream.core.service.security.dto.ResourcePermissionDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ResourceDtoMapper extends BaseMapper<ResourceDto, Resource> {


    @Override
    @Mapping(source = "menuId", target = "menu.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Resource toEntity(ResourceDto dto);

    @Override
    @Mapping(source = "menu.id", target = "menuId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ResourceDto toDto(Resource entity);

    @Override
    @Mapping(source = "menuId", target = "menu.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Resource> toEntityList(List<ResourceDto> dtoList);

    @Override
    @Mapping(source = "menu.id", target = "menuId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<ResourceDto> toDtoList(List<Resource> entityList);
}
