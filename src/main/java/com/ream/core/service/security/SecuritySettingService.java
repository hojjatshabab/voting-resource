package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.SecuritySettingDto;

import java.util.Optional;
import java.util.UUID;

public interface SecuritySettingService {

    SecuritySettingDto save(SecuritySettingDto securitySettingDto);

    SecuritySettingDto update(SecuritySettingDto securitySettingDto);

    PageResponse<SecuritySettingDto> findAll(PageRequest<SecuritySettingDto> model);
    Optional<SecuritySettingDto> findById(UUID id);

    Boolean deleteById(UUID id);


}
