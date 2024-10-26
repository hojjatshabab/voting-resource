package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Permission;
import com.ream.core.domain.security.ResourcePermission;
import com.ream.core.repository.security.PermissionRepository;
import com.ream.core.repository.security.ResourcePermissionRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.PermissionService;
import com.ream.core.service.security.dto.PermissionDto;
import com.ream.core.service.security.dto.ResourcePermissionDto;
import com.ream.core.service.security.mapper.PermissionDtoMapper;
import com.ream.core.service.security.mapper.ResourcePermissionDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    PermissionRepository permissionRepository;


    @Autowired
    PermissionDtoMapper permissionDtoMapper;



    @Override
    public PermissionDto save(PermissionDto permissionDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return permissionDtoMapper.toDto(permissionRepository.save(permissionDtoMapper.toEntity(permissionDto)));
    }

    @Override
    public PermissionDto update(PermissionDto permissionDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return permissionDtoMapper.toDto(permissionRepository.save(permissionDtoMapper.toEntity(permissionDto)));

    }

    @Override
    public PageResponse<PermissionDto> findAll(PageRequest<PermissionDto> model) {
        List<PermissionDto> result = permissionRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(permissionDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = permissionRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<PermissionDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (optionalPermission.isPresent()) {
            return Optional.ofNullable(permissionDtoMapper.toDto(optionalPermission.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        permissionRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
