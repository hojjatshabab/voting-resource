package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Role;
import com.ream.core.domain.security.UserRole;
import com.ream.core.repository.security.RoleRepository;
import com.ream.core.repository.security.UserRepository;
import com.ream.core.repository.security.UserRoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.UserRoleService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.UserRoleDto;
import com.ream.core.service.security.mapper.RoleDtoMapper;
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
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;


    @Autowired
    UserRoleDtoMapper userRoleDtoMapper;

    @Override
    public UserRoleDto save(UserRoleDto userRoleDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return userRoleDtoMapper.toDto(userRoleRepository.save(userRoleDtoMapper.toEntity(userRoleDto)));
    }

    @Override
    public UserRoleDto update(UserRoleDto userRoleDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return userRoleDtoMapper.toDto(userRoleRepository.save(userRoleDtoMapper.toEntity(userRoleDto)));

    }

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<List<UserRoleDto>> findByUserId(UUID userId) {
        List<UserRole> userRoles = userRoleRepository.findByUser(userRepository.findById(userId).get());
        if (userRoles.size() > 0) {
            return Optional.ofNullable(userRoleDtoMapper.toDtoList(userRoles));
        }
        return Optional.empty();
    }

    @Override
    public PageResponse<UserRoleDto> findAll(PageRequest<UserRoleDto> model) {
        List<UserRoleDto> result = userRoleRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(userRoleDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = userRoleRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<UserRoleDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<UserRole> optionalUser = userRoleRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(userRoleDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        userRoleRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }

}
