package com.ream.core.service.security.impl;

import com.ream.core.domain.security.SecuritySetting;
import com.ream.core.domain.security.UserRole;
import com.ream.core.repository.security.SecuritySettingRepository;
import com.ream.core.repository.security.UserRoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.SecuritySettingService;
import com.ream.core.service.security.dto.SecuritySettingDto;
import com.ream.core.service.security.dto.UserRoleDto;
import com.ream.core.service.security.mapper.SecuritySettingDtoMapper;
import com.ream.core.service.security.mapper.UserRoleDtoMapper;
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
public class SecuritySettingServiceImpl implements SecuritySettingService {


    @Autowired
    SecuritySettingRepository securitySettingRepository;


    @Autowired
    SecuritySettingDtoMapper securitySettingDtoMapper;

    @Override
    public SecuritySettingDto save(SecuritySettingDto securitySettingDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return securitySettingDtoMapper.toDto(securitySettingRepository.save(securitySettingDtoMapper.toEntity(securitySettingDto)));
    }

    @Override
    public SecuritySettingDto update(SecuritySettingDto securitySettingDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return securitySettingDtoMapper.toDto(securitySettingRepository.save(securitySettingDtoMapper.toEntity(securitySettingDto)));

    }

    @Override
    public PageResponse<SecuritySettingDto> findAll(PageRequest<SecuritySettingDto> model) {
        List<SecuritySettingDto> result = securitySettingRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(securitySettingDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = securitySettingRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<SecuritySettingDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<SecuritySetting> optionalSecuritySetting = securitySettingRepository.findById(id);
        if (optionalSecuritySetting.isPresent()) {
            return Optional.ofNullable(securitySettingDtoMapper.toDto(optionalSecuritySetting.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        securitySettingRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }
}
