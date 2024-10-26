package com.ream.core.service.security.mapper;

import com.ream.core.domain.security.UserRole;
import com.ream.core.service.BaseMapper;

import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import com.ream.core.service.security.dto.UserRoleDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserRoleDtoMapper extends BaseMapper<UserRoleDto, UserRole> {



    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "roleRolePersianName", target = "role.rolePersianName")
    @Mapping(source = "roleRoleLatinName", target = "role.roleLatinName")
    @Mapping(source = "userUsername", target = "user.username")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserRole toEntity(UserRoleDto dto);

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.rolePersianName", target = "roleRolePersianName")
    @Mapping(source = "role.roleLatinName", target = "roleRoleLatinName")
    @Mapping(source = "user.username", target = "userUsername")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserRoleDto toDto(UserRole entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "roleRolePersianName", target = "role.rolePersianName")
    @Mapping(source = "roleRoleLatinName", target = "role.roleLatinName")
    @Mapping(source = "userUsername", target = "user.username")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<UserRole> toEntityList(List<UserRoleDto> dtoList);

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.rolePersianName", target = "roleRolePersianName")
    @Mapping(source = "role.roleLatinName", target = "roleRoleLatinName")
    @Mapping(source = "user.username", target = "userUsername")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<UserRoleDto> toDtoList(List<UserRole> entityList);



}
