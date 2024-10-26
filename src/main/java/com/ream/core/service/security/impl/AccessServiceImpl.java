package com.ream.core.service.security.impl;

import com.ream.core.config.JwtUtils;
import com.ream.core.domain.security.Access;
import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.UserRole;
import com.ream.core.repository.security.AccessRepository;
import com.ream.core.repository.security.LogHistoryRepository;
import com.ream.core.repository.security.RoleRepository;
import com.ream.core.repository.security.UserRoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.*;
import com.ream.core.service.security.dto.*;
import com.ream.core.service.security.mapper.AccessDtoMapper;
import com.ream.core.service.security.mapper.LogHistoryDtoMapper;
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
public class AccessServiceImpl implements AccessService {


    @Autowired
    AccessRepository accessRepository;


    @Autowired
    AccessDtoMapper accessDtoMapper;

    @Override
    public AccessDto save(AccessDto accessDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return accessDtoMapper.toDto(accessRepository.save(accessDtoMapper.toEntity(accessDto)));
    }

    @Override
    public AccessDto update(AccessDto accessDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return accessDtoMapper.toDto(accessRepository.save(accessDtoMapper.toEntity(accessDto)));

    }

    @Override
    public PageResponse<AccessDto> findAll(PageRequest<AccessDto> model) {
        List<AccessDto> result = accessRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(accessDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = accessRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<AccessDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<Access> optionalAccess = accessRepository.findById(id);
        if (optionalAccess.isPresent()) {
            return Optional.ofNullable(accessDtoMapper.toDto(optionalAccess.get()));
        }
        return Optional.empty();
    }

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public AccessAddressSerializable getAccessAddress(String token) {
        AccessAddressSerializable accessAddressSerializable = new AccessAddressSerializable();
        try {
            List<Access> list = new ArrayList<>();
            List<UUID> AccessIds = new ArrayList<>();
            String[] parts = jwtUtils.extractAuthorities(token).toString().split(",");
            List<String> strings = Arrays.stream(parts).toList();
            // List<Map<String, Object>> lists = (List<Map<String, Object>>) jwtUtils.extractAuthorities(token);
            if (strings.size() > 0) {
                if (strings.size() == 1 && strings.get(0).isEmpty()) {

                }
                    else{
                    for (String authority : strings) {
                        UUID AccessId = UUID.fromString(authority.toString());
                        AccessIds.add(AccessId);
                    }
                    list = accessRepository.findByIdIn(AccessIds);
                }
            }

            Boolean isAdmin = false;

            Optional<List<UserRoleDto>> roleList = userRoleService.findByUserId(userService.getCurrentUser().getId());
            if (roleList.isPresent()) {
                isAdmin = roleList.get().stream().filter(f -> f.getRoleRoleLatinName().equals("administrator")).collect(Collectors.toList()).size() > 0 ? true : false;
            }
            accessAddressSerializable.setAdmin(isAdmin);
            List<AccessAddressDto> accessAddressDtos = new ArrayList<>();
            for (Access access : list) {
                AccessAddressDto accessAddressDto = new AccessAddressDto();
                String resourceUrl = access.getResourcePermission().getResource().getUrl();
                String permissionUrl = access.getResourcePermission().getPermission().getUrl();
                String permissionMethod = access.getResourcePermission().getPermission().getHttpRequestMethod();
                accessAddressDto.setMainUrl(resourceUrl);
                accessAddressDto.setUrl(resourceUrl.concat(Objects.isNull(permissionUrl) ? "" : permissionUrl));
                accessAddressDto.setRequestType(permissionMethod);
                accessAddressDtos.add(accessAddressDto);
            }
            accessAddressSerializable.setAccessAddressDtos(accessAddressDtos);
        } catch (Exception e) {
        }
        return accessAddressSerializable;
    }

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Override
    public Optional<AccessRoleDto> getAccessRoleDtoByCurrentUser() {
        AccessRoleDto accessRoleDto = new AccessRoleDto();
        UserDto userDto = userService.getCurrentUser();
        if (Objects.nonNull(userDto)) {
            Set<String> permissionKey = new HashSet<>();
            Set<String> resourceKey = new HashSet<>();
            Set<String> rolekey = new HashSet<>();
            Set<String> menuKey = new HashSet<>();
            Optional<List<UserRoleDto>> userRoleDtos = userRoleService.findByUserId(userDto.getId());
            Boolean isAdmin = false;
            if (userRoleDtos.isPresent()) {
                isAdmin =
                        userRoleDtos.get().stream().filter(f -> f.getRoleRoleLatinName().equals("administrator")).collect(Collectors.toList()).size() > 0 ? true : false;
                if (!isAdmin) {
                    for (UserRoleDto userRoleDto : userRoleDtos.get()) {
                        rolekey.add(userRoleDto.getRoleRoleLatinName());
                        List<Access> accesses = accessRepository.findByRoleId(userRoleDto.getRoleId());
                        for (Access access : accesses) {
                            String resourceKee = access.getResourcePermission().getResource().getKey();
                            String permissionKee = access.getResourcePermission().getPermission().getKey();
                            permissionKey.add(resourceKee.concat(permissionKee));
                            resourceKey.add(resourceKee);
                            menuKey.addAll(menuService.getMenuKeyByMenuId(access.getResourcePermission().getResource().getMenu().getId()));
                        }
                    }

                } else {
                    rolekey.addAll(userRoleDtos.get().stream().map(m -> m.getRoleRoleLatinName()).collect(Collectors.toList()));
                }

            }
            accessRoleDto.setAdmin(isAdmin);
            accessRoleDto.setRoleName(rolekey);
            accessRoleDto.setPermissionKey(permissionKey);
            accessRoleDto.setResourceKey(resourceKey);
            accessRoleDto.setMenuKey(menuKey);
            return Optional.ofNullable(accessRoleDto);
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        accessRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
