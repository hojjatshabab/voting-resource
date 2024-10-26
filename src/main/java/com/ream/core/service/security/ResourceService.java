package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.ResourceDto;
import com.ream.core.service.security.dto.RoleDto;

import java.util.Optional;
import java.util.UUID;

public interface ResourceService {
    ResourceDto save(ResourceDto resourceDto);

    ResourceDto update(ResourceDto resourceDto);

    PageResponse<ResourceDto> findAll(PageRequest<ResourceDto> model);
    Optional<ResourceDto> findById(UUID id);

    Boolean deleteById(UUID id);
}
