package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.UserRoleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleService {

    UserRoleDto save(UserRoleDto userRoleDto);

    UserRoleDto update(UserRoleDto userRoleDto);


    Optional<List<UserRoleDto>> findByUserId(UUID userId);

    PageResponse<UserRoleDto> findAll(PageRequest<UserRoleDto> model);

    Optional<UserRoleDto> findById(UUID id);

    Boolean deleteById(UUID id);


}
