package com.ream.core.service.baseInfo.mapper;


import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import com.ream.core.service.baseInfo.dto.PerformerDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PerformerDtoMapper extends BaseMapper<PerformerDto, OrganizationUnit> {

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrganizationUnit toEntity(PerformerDto dto);


    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PerformerDto toDto(OrganizationUnit entity);


    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<OrganizationUnit> toEntityList(List<PerformerDto> dtoList);


    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<PerformerDto> toDtoList(List<OrganizationUnit> entityList);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrganizationUnit update(PerformerDto Dto, @MappingTarget OrganizationUnit Entity);

    @AfterMapping
    default void convertToNull(@MappingTarget OrganizationUnit organizationUnit) {


        if (Objects.isNull(organizationUnit.getCity()) || Objects.isNull(organizationUnit.getCity().getId())) {
            organizationUnit.setCity(null);
        }
        if (Objects.isNull(organizationUnit.getCommonBaseDataProvince()) || Objects.isNull(organizationUnit.getCommonBaseDataProvince().getId())) {
            organizationUnit.setCommonBaseDataProvince(null);
        }
        if (Objects.isNull(organizationUnit.getCommonBaseDataGeometricLocation()) || Objects.isNull(organizationUnit.getCommonBaseDataGeometricLocation().getId())) {
            organizationUnit.setCommonBaseDataGeometricLocation(null);
        }
        if (Objects.isNull(organizationUnit.getCommonBaseDataUnitType()) || Objects.isNull(organizationUnit.getCommonBaseDataUnitType().getId())) {
            organizationUnit.setCommonBaseDataUnitType(null);
        }
        if (Objects.isNull(organizationUnit.getCommonBaseDataFundamentalBase()) || Objects.isNull(organizationUnit.getCommonBaseDataFundamentalBase().getId())) {
            organizationUnit.setCommonBaseDataFundamentalBase(null);
        }
    }


    @AfterMapping
    default void convertToNull(@MappingTarget OrganizationUnitDto organizationUnitDto) {
        if (Objects.isNull(organizationUnitDto.getCityId())) {
            organizationUnitDto.setCityName(null);
            organizationUnitDto.setCityId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataProvinceId())) {
            organizationUnitDto.setCommonBaseDataProvinceName(null);
            organizationUnitDto.setCommonBaseDataProvinceId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataGeometricLocationId())) {
            organizationUnitDto.setCommonBaseDataGeometricLocationName(null);
            organizationUnitDto.setCommonBaseDataGeometricLocationId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataUnitTypeId())) {
            organizationUnitDto.setCommonBaseDataUnitTypeName(null);
            organizationUnitDto.setCommonBaseDataUnitTypeId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataFundamentalBaseId())) {
            organizationUnitDto.setCommonBaseDataFundamentalBaseName(null);
            organizationUnitDto.setCommonBaseDataFundamentalBaseId(null);
        }
    }

}
