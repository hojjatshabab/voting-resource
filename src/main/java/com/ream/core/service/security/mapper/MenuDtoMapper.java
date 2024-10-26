package com.ream.core.service.security.mapper;

import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.domain.security.Menu;
import com.ream.core.domain.security.Permission;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import com.ream.core.service.security.dto.MenuDto;
import com.ream.core.service.security.dto.PermissionDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MenuDtoMapper extends BaseMapper<MenuDto, Menu> {


    @Override
    @Mapping(source = "parentId", target = "parent.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Menu toEntity(MenuDto dto);

    @Override
    @Mapping(source = "parent.id", target = "parentId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MenuDto toDto(Menu entity);

    @Override
    @Mapping(source = "parentId", target = "parent.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Menu> toEntityList(List<MenuDto> dtoList);

    @Override
    @Mapping(source = "parent.id", target = "parentId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MenuDto> toDtoList(List<Menu> entityList);


    @AfterMapping
    default void convertToNull(@MappingTarget Menu menu) {


        if (Objects.isNull(menu.getParent()) || Objects.isNull(menu.getParent().getId())) {
            menu.setParent(null);
        }
    }


    @AfterMapping
    default void convertToNull(@MappingTarget MenuDto menuDto) {
        if (Objects.isNull(menuDto.getParentId())) {
            menuDto.setParentId(null);
        }
    }


}
