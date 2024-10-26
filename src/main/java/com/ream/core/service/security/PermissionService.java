package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.PermissionDto;
import com.ream.core.service.security.dto.ResourcePermissionDto;

import java.util.Optional;
import java.util.UUID;

public interface PermissionService {

    PermissionDto save(PermissionDto permissionDto);

    PermissionDto update(PermissionDto permissionDto);

    PageResponse<PermissionDto> findAll(PageRequest<PermissionDto> model);
    Optional<PermissionDto> findById(UUID id);

    Boolean deleteById(UUID id);


}
