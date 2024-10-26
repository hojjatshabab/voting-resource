package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.AccessAddressSerializable;
import com.ream.core.service.security.dto.AccessDto;
import com.ream.core.service.security.dto.AccessRoleDto;
import com.ream.core.service.security.dto.LogHistoryDto;

import java.util.Optional;
import java.util.UUID;

public interface AccessService {


    AccessDto save(AccessDto accessDto);

    AccessDto update(AccessDto accessDto);

    PageResponse<AccessDto> findAll(PageRequest<AccessDto> model);

    Optional<AccessDto> findById(UUID id);


    Optional<AccessRoleDto> getAccessRoleDtoByCurrentUser();

    AccessAddressSerializable getAccessAddress(String token);


    Boolean deleteById(UUID id);
}
