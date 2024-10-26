package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.ResourcePermissionDto;
import com.ream.core.service.security.dto.RoleDto;

import java.util.Optional;
import java.util.UUID;

public interface ResourcePermissionService  {


    ResourcePermissionDto save(ResourcePermissionDto resourcePermissionDto);

    ResourcePermissionDto update(ResourcePermissionDto resourcePermissionDto);

    PageResponse<ResourcePermissionDto> findAll(PageRequest<ResourcePermissionDto> model);
    Optional<ResourcePermissionDto> findById(UUID id);

    Boolean deleteById(UUID id);
}
