package com.ream.core.service.baseInfo.impl;

import com.ream.core.domain.baseInfo.CommonBaseData;
import com.ream.core.domain.baseInfo.CommonBaseType;
import com.ream.core.repository.baseInfo.CommonBaseDataRepository;
import com.ream.core.repository.baseInfo.CommonBaseTypeRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CommonBaseDataService;
import com.ream.core.service.baseInfo.dto.CommonBaseDataDto;
import com.ream.core.service.baseInfo.mapper.CommonBaseDataDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommonBaseDataServiceImpl implements CommonBaseDataService {
    @Autowired
    CommonBaseDataDtoMapper commonBaseDataDtoMapper;
    @Autowired CommonBaseDataRepository commonBaseDataRepository;
    @Autowired CommonBaseTypeRepository commonBaseTypeRepository;

    @Override
    public CommonBaseDataDto save(CommonBaseDataDto commonBaseDataDto) {
//        log.info("Save new common_base_data {} in to database.", commonBaseDataDto.getValue());

        return commonBaseDataDtoMapper.toDto(commonBaseDataRepository.save(commonBaseDataDtoMapper.toEntity(commonBaseDataDto)));
    }

    @Override
    public CommonBaseDataDto update(CommonBaseDataDto commonBaseDataDto) {
//        log.info("Update common_base_data {} in to database.", commonBaseDataDto.getValue());
        return commonBaseDataDtoMapper.toDto(commonBaseDataRepository.save(commonBaseDataDtoMapper.toEntity(commonBaseDataDto))

        );

    }

    @Override
    public PageResponse<CommonBaseDataDto> findAll(PageRequest<CommonBaseDataDto> model) {
//        log.info("Finding all common_base_data.");
        List<CommonBaseDataDto> result = commonBaseDataRepository.findAll(Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(commonBaseDataDtoMapper::toDto).collect(Collectors.toList());
        long count = commonBaseDataRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public PageResponse<CommonBaseDataDto> findByClassName(String className, PageRequest<CommonBaseDataDto> model) {
//        log.info("Finding all common_base_data in common_base_type {}.", className);
        Optional<CommonBaseType> optionalCommonBaseType = commonBaseTypeRepository.findByClassName(className);
        if (!optionalCommonBaseType.isPresent()) {
            return null;
        }
        List<CommonBaseDataDto> result = commonBaseDataRepository.findByCommonBaseType(optionalCommonBaseType.get(), Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(commonBaseDataDtoMapper::toDto).collect(Collectors.toList());
        long count = commonBaseDataRepository.count();
        return new PageResponse<>(result.stream().sorted((s1, s2) -> s1.getOrderNo().compareTo(s2.getOrderNo())).collect(Collectors.toList()), model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<List<CommonBaseDataDto>> findAllByClassName(String className) {
//        log.info("Finding all common_base_data in common_base_type {}.", className);
        if (Objects.nonNull(className)) {
            Optional<CommonBaseType> optionalCommonBaseType = commonBaseTypeRepository.findByClassName(className);
            if (optionalCommonBaseType.isPresent()) {
                List<CommonBaseData> result = commonBaseDataRepository.findByCommonBaseTypeOrderByOrderNoAsc(optionalCommonBaseType.get());

                if (result.size() > 0) return Optional.ofNullable(commonBaseDataDtoMapper.toDtoList(result));
            }

        }
        return Optional.empty();
    }

    @Override
    public Optional<CommonBaseDataDto> findById(Long id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<CommonBaseData> optionalCommonBaseData = commonBaseDataRepository.findById(id);
        if (optionalCommonBaseData.isPresent()) {
            return Optional.ofNullable(commonBaseDataDtoMapper.toDto(optionalCommonBaseData.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(Long id) {
        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        commonBaseDataRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }

    @Override
    public List<CommonBaseDataDto> search(String pattern) {
        return commonBaseDataDtoMapper.toDtoList(commonBaseDataRepository.search(pattern));
    }


    @Override
    public PageResponse<CommonBaseDataDto> findByValueAndType(String value, String className, PageRequest<CommonBaseDataDto> model) {
//        log.info("Finding all common_base_data in value {}.", value);
        Optional<CommonBaseType> optionalCommonBaseType = commonBaseTypeRepository.findByClassName(className);
        if (!optionalCommonBaseType.isPresent()) {
            return null;
        }
        List<CommonBaseDataDto> result = commonBaseDataRepository.findByValueContainsAndCommonBaseType(value, optionalCommonBaseType.get(), Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(commonBaseDataDtoMapper::toDto).collect(Collectors.toList());
        long count = result.size();
        return new PageResponse<>(result.stream().sorted((s1, s2) -> s1.getOrderNo().compareTo(s2.getOrderNo())).collect(Collectors.toList()), model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }
}
