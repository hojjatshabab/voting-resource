package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.RoleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleService {

    RoleDto save(RoleDto roleDto);

    RoleDto update(RoleDto roleDto);

    PageResponse<RoleDto> findAll(PageRequest<RoleDto> model);
    Optional<RoleDto> findById(UUID id);

    List<RoleDto> findAll();

    Optional<List<RoleDto>> findRoleNotUser(UUID userId);

    Boolean deleteById(UUID id);


}
