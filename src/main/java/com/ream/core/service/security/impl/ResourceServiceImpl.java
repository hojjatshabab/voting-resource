package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Resource;
import com.ream.core.domain.security.Role;
import com.ream.core.repository.security.ResourceRepository;
import com.ream.core.repository.security.RoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.ResourceService;
import com.ream.core.service.security.dto.ResourceDto;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.mapper.ResourceDtoMapper;
import com.ream.core.service.security.mapper.RoleDtoMapper;
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
public class ResourceServiceImpl implements ResourceService {



    @Autowired
    ResourceRepository resourceRepository;


    @Autowired
    ResourceDtoMapper resourceDtoMapper;

    @Override
    public ResourceDto save(ResourceDto resourceDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return resourceDtoMapper.toDto(resourceRepository.save(resourceDtoMapper.toEntity(resourceDto)));
    }

    @Override
    public ResourceDto update(ResourceDto resourceDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return resourceDtoMapper.toDto(resourceRepository.save(resourceDtoMapper.toEntity(resourceDto)));

    }

    @Override
    public PageResponse<ResourceDto> findAll(PageRequest<ResourceDto> model) {
        List<ResourceDto> result = resourceRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(resourceDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = resourceRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<ResourceDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<Resource> optionalUser = resourceRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(resourceDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        resourceRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
