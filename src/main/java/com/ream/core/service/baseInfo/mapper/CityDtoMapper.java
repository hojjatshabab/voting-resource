package com.ream.core.service.baseInfo.mapper;

import com.ream.core.domain.baseInfo.City;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.CityDto;
import org.mapstruct.*;


import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CityDtoMapper extends BaseMapper<CityDto, City> {


    @Override
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City toEntity(CityDto dto);


    @Override
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CityDto toDto(City entity);


    @Override
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<City> toEntityList(List<CityDto> dtoList);


    @Override
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<CityDto> toDtoList(List<City> entityList);

    @Override
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City update(CityDto Dto, @MappingTarget City Entity);


}
