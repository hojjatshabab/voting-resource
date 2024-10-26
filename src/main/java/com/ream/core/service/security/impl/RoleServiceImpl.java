package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Role;
import com.ream.core.domain.security.UserRole;
import com.ream.core.repository.security.RoleRepository;
import com.ream.core.repository.security.UserRepository;
import com.ream.core.repository.security.UserRoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.mapper.RoleDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    @Autowired
    RoleRepository roleRepository;


    @Autowired
    RoleDtoMapper roleDtoMapper;

    @Override
    public RoleDto save(RoleDto roleDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return roleDtoMapper.toDto(roleRepository.save(roleDtoMapper.toEntity(roleDto)));
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return roleDtoMapper.toDto(roleRepository.save(roleDtoMapper.toEntity(roleDto)));

    }

    @Override
    public PageResponse<RoleDto> findAll(PageRequest<RoleDto> model) {
        List<RoleDto> result = roleRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(roleDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = roleRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<RoleDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<Role> optionalUser = roleRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(roleDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> list = roleRepository.findAll();
        if (list.size() > 0) {
            return roleDtoMapper.toDtoList(list);
        }
        return new ArrayList<>();
    }

    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<List<RoleDto>> findRoleNotUser(UUID userId) {
        List<UUID> roleId = new ArrayList<>();
        List<UserRole> userRoleList = userRoleRepository.findByUser(userRepository.findById(userId).get());
        if (userRoleList.size() > 0) {
            roleId.addAll(userRoleList.stream().map(m -> m.getRole().getId()).collect(Collectors.toList()));
        }
        List<Role> roleList;
        if (roleId.size() > 0) {
            roleList = roleRepository.findByIdNotIn(roleId);
        } else {
            roleList = roleRepository.findAll();
        }
        if (roleList.size() > 0) {
            return Optional.ofNullable(roleDtoMapper.toDtoList(roleList));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        roleRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
