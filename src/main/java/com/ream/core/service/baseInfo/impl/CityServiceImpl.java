package com.ream.core.service.baseInfo.impl;

import com.ream.core.domain.baseInfo.City;
import com.ream.core.domain.baseInfo.CommonBaseData;
import com.ream.core.repository.baseInfo.CityRepository;
import com.ream.core.repository.baseInfo.CommonBaseDataRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CityService;
import com.ream.core.service.baseInfo.dto.CityDto;
import com.ream.core.service.baseInfo.mapper.CityDtoMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    @Autowired
    CommonBaseDataRepository commonBaseDataRepository;
    @Autowired CityRepository cityRepository;
    @Autowired CityDtoMapper cityDtoMapper;

    @Override
    public CityDto save(CityDto cityDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return cityDtoMapper.toDto(cityRepository.save(cityDtoMapper.toEntity(cityDto)));
    }

    @Override
    public CityDto update(CityDto cityDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return cityDtoMapper.toDto(cityRepository.save(cityDtoMapper.toEntity(cityDto))
        );

    }

    @Override
    public PageResponse<CityDto> findAll(Long province, String name, PageRequest<CityDto> model) {
//        log.info("Finding all common_base_data.");
        List<CityDto> result = cityDtoMapper.toDtoList(cityRepository.findAllByProvinceAndName(province, name, model.getPageSize(), model.getCurrentPage()));
        long count = result.size();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());

    }

    @Override
    public Optional<CityDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<City> optionalCityDto = cityRepository.findById(id);
        if (optionalCityDto.isPresent()) {
            return Optional.ofNullable(cityDtoMapper.toDto(optionalCityDto.get()));
        }
        return Optional.empty();
    }


    @Override
    public List<CityDto> findByCommonBaseDataProvince(Long provinceId) {
//        log.info("Finding common_base_data by province {} .", provinceId.toString());
        Optional<CommonBaseData> commonBaseDataOptional = commonBaseDataRepository.findById(provinceId);
        if (commonBaseDataRepository.findById(provinceId).isPresent()) {
            return cityDtoMapper.toDtoList(cityRepository.findByCommonBaseDataProvince(commonBaseDataOptional.get()));
        }
        return null;
    }

    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        cityRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


    @Override
    public Optional<List<CityDto>> findByNameContains(String name) {
        Optional<List<City>> cityList = cityRepository.findByNameContains(name);
        if (cityList.get().size() > 0)
            return Optional.ofNullable(cityDtoMapper.toDtoList(cityList.get()));
        return Optional.empty();
    }

    @Override
    public CityDto findByCode(String code) {
//        log.info("city find city by code {} .", code);
        return cityDtoMapper.toDto(cityRepository.findByCode(code));
    }
}
