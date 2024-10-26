package com.ream.core.service.baseInfo.impl;

import com.ream.core.domain.baseInfo.Type;
import com.ream.core.repository.baseInfo.CommonBaseDataRepository;
import com.ream.core.repository.baseInfo.CommonBaseTypeRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CommonBaseTypeService;
import com.ream.core.service.baseInfo.dto.TypeDto;
import com.ream.core.service.baseInfo.mapper.CommonBaseTypeDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class CommonBaseTypeServiceImpl implements CommonBaseTypeService {
    @Autowired
    CommonBaseTypeRepository commonBaseTypeRepository;
    @Autowired
    CommonBaseDataRepository commonBaseDataRepository;
    @Autowired
    CommonBaseTypeDtoMapper commonBaseTypeDtoMapper;

    @Override
    public TypeDto save(TypeDto commonBaseTypeDto) {
//        log.info("Save new common_base_type {} in to database.", commonBaseTypeDto.getTitle());
        return commonBaseTypeDtoMapper.toDto(
                commonBaseTypeRepository.save(
                        commonBaseTypeDtoMapper.toEntity(commonBaseTypeDto))
        );
    }

    @Override
    public TypeDto update(TypeDto commonBaseTypeDto) {
//        log.info("Update common_base_type {} in to database.", commonBaseTypeDto.getTitle());
        return commonBaseTypeDtoMapper.toDto(
                commonBaseTypeRepository.save(
                        commonBaseTypeDtoMapper.toEntity(commonBaseTypeDto))
        );
    }

    @Override
    public Optional<TypeDto> findById(Long id) {
//        log.info("Finding common_base_type by id {} .", id);
        Optional<Type> optionalCommonBaseType = commonBaseTypeRepository.findById(id);
        if (optionalCommonBaseType.isPresent()) {
            return Optional.ofNullable(
                    commonBaseTypeDtoMapper.toDto(optionalCommonBaseType.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TypeDto> findByClassName(String className) {
//        log.info("Finding common_base_type by class name {} .", className);
        Optional<Type> optionalCommonBaseType = commonBaseTypeRepository.findByClassName(className);
        if (optionalCommonBaseType.isPresent()) {
            return Optional.ofNullable(
                    commonBaseTypeDtoMapper.toDto(optionalCommonBaseType.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<TypeDto>> findByTitleContains(String name) {
        Optional<List<Type>> commonBaseTypeDto = commonBaseTypeRepository.findByTitleContains(name);
        if (commonBaseTypeDto.get().size() > 0)
            return Optional.ofNullable(commonBaseTypeDtoMapper.toDtoList(commonBaseTypeDto.get()));
        return Optional.empty();
    }


    @Override
    public PageResponse<TypeDto> findByClassNameOrTitle(String className, String title, PageRequest<TypeDto> model) {
//        log.info("Finding all className and title  in common_base_type {}.", className, title);
        List<TypeDto> result;
        if (className.isBlank() && title.isBlank())
            return findAll(model);
        if (!className.isBlank() && !title.isBlank())
            result = commonBaseTypeRepository.findByClassNameContainingAndTitleContaining(className.isBlank() ? null : className.trim(), title.isBlank() ? null : title.trim(), Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(commonBaseTypeDtoMapper::toDto).collect(Collectors.toList());
        else
            result = commonBaseTypeRepository.findByClassNameContainingOrTitleContaining(className.isBlank() ? null : className.trim(), title.isBlank() ? null : title.trim(), Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(commonBaseTypeDtoMapper::toDto).collect(Collectors.toList());
        long count = result.size();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public PageResponse<TypeDto> findAll(PageRequest<TypeDto> model) {
//        log.info("Finding all common_base_type.");
        List<TypeDto> result = commonBaseTypeRepository
                .findAll(
                        //  Example.of(example, ExampleMatcher.matching().withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())),
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(commonBaseTypeDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = commonBaseTypeRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Boolean deleteById(Long id) {
        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_type by id {} .", id);
            return false;
        }
        commonBaseTypeRepository.deleteById(id);
//        log.info("Success delete common_base_type by id {} .", id);
        return true;
    }
}
