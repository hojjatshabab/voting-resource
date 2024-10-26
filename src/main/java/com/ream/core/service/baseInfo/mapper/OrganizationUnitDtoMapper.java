package com.ream.core.service.baseInfo.mapper;


import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import org.mapstruct.*;


import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrganizationUnitDtoMapper extends BaseMapper<OrganizationUnitDto, OrganizationUnit> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "commonBaseDataFundamentalBaseId", target = "commonBaseDataFundamentalBase.id")
    @Mapping(source = "commonBaseDataGeometricLocationId", target = "commonBaseDataGeometricLocation.id")
    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "commonBaseDataUnitTypeId", target = "commonBaseDataUnitType.id")
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrganizationUnit toEntity(OrganizationUnitDto dto);


    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "commonBaseDataFundamentalBase.id", target = "commonBaseDataFundamentalBaseId")
    @Mapping(source = "commonBaseDataFundamentalBase.value", target = "commonBaseDataFundamentalBaseName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "commonBaseDataUnitType.id", target = "commonBaseDataUnitTypeId")
    @Mapping(source = "commonBaseDataUnitType.value", target = "commonBaseDataUnitTypeName")
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @Mapping(source = "commonBaseDataGeometricLocation.id", target = "commonBaseDataGeometricLocationId")
    @Mapping(source = "commonBaseDataGeometricLocation.value", target = "commonBaseDataGeometricLocationName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrganizationUnitDto toDto(OrganizationUnit entity);


    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "commonBaseDataFundamentalBaseId", target = "commonBaseDataFundamentalBase.id")
    @Mapping(source = "commonBaseDataGeometricLocationId", target = "commonBaseDataGeometricLocation.id")
    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "commonBaseDataUnitTypeId", target = "commonBaseDataUnitType.id")
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<OrganizationUnit> toEntityList(List<OrganizationUnitDto> dtoList);


    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "commonBaseDataFundamentalBase.id", target = "commonBaseDataFundamentalBaseId")
    @Mapping(source = "commonBaseDataFundamentalBase.value", target = "commonBaseDataFundamentalBaseName")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "parent.name", target = "parentName")
    @Mapping(source = "commonBaseDataUnitType.id", target = "commonBaseDataUnitTypeId")
    @Mapping(source = "commonBaseDataUnitType.value", target = "commonBaseDataUnitTypeName")
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @Mapping(source = "commonBaseDataGeometricLocation.id", target = "commonBaseDataGeometricLocationId")
    @Mapping(source = "commonBaseDataGeometricLocation.value", target = "commonBaseDataGeometricLocationName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<OrganizationUnitDto> toDtoList(List<OrganizationUnit> entityList);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "commonBaseDataFundamentalBaseId", target = "commonBaseDataFundamentalBase.id")
    @Mapping(source = "commonBaseDataGeometricLocationId", target = "commonBaseDataGeometricLocation.id")
    @Mapping(source = "parentId", target = "parent.id")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "commonBaseDataUnitTypeId", target = "commonBaseDataUnitType.id")
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrganizationUnit update(OrganizationUnitDto Dto,@MappingTarget OrganizationUnit Entity);

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
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataProvinceId()) ) {
            organizationUnitDto.setCommonBaseDataProvinceName(null);
            organizationUnitDto.setCommonBaseDataProvinceId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataGeometricLocationId()) ) {
            organizationUnitDto.setCommonBaseDataGeometricLocationName(null);
            organizationUnitDto.setCommonBaseDataGeometricLocationId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataUnitTypeId())) {
            organizationUnitDto.setCommonBaseDataUnitTypeName(null);
            organizationUnitDto.setCommonBaseDataUnitTypeId(null);
        }
        if (Objects.isNull(organizationUnitDto.getCommonBaseDataFundamentalBaseId()) ) {
            organizationUnitDto.setCommonBaseDataFundamentalBaseName(null);
            organizationUnitDto.setCommonBaseDataFundamentalBaseId(null);
        }
    }






}
