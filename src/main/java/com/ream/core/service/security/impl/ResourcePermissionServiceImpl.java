package com.ream.core.service.security.impl;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.ResourcePermission;
import com.ream.core.repository.security.LogHistoryRepository;
import com.ream.core.repository.security.ResourcePermissionRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.ResourcePermissionService;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.ResourcePermissionDto;
import com.ream.core.service.security.mapper.LogHistoryDtoMapper;
import com.ream.core.service.security.mapper.ResourcePermissionDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ResourcePermissionServiceImpl  implements ResourcePermissionService {
    @Autowired
    ResourcePermissionRepository resourcePermissionRepository;


    @Autowired
    ResourcePermissionDtoMapper resourcePermissionDtoMapper;



    @Override
    public ResourcePermissionDto save(ResourcePermissionDto resourcePermissionDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return resourcePermissionDtoMapper.toDto(resourcePermissionRepository.save(resourcePermissionDtoMapper.toEntity(resourcePermissionDto)));
    }

    @Override
    public ResourcePermissionDto update(ResourcePermissionDto resourcePermissionDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return resourcePermissionDtoMapper.toDto(resourcePermissionRepository.save(resourcePermissionDtoMapper.toEntity(resourcePermissionDto)));

    }

    @Override
    public PageResponse<ResourcePermissionDto> findAll(PageRequest<ResourcePermissionDto> model) {
        List<ResourcePermissionDto> result = resourcePermissionRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(resourcePermissionDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = resourcePermissionRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<ResourcePermissionDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<ResourcePermission> optionalUser = resourcePermissionRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(resourcePermissionDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        resourcePermissionRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }




}
