package com.ream.core.service.security;

import com.ream.core.domain.security.RefreshToken;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.PermissionDto;
import com.ream.core.service.security.dto.RefreshTokenDto;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {


    String createRefreshToken(UUID userId);

    RefreshToken validateRefreshToken(String token);

    void deleteRefreshToken(UUID userId);

    RefreshTokenDto save(RefreshTokenDto refreshTokenDto);

    RefreshTokenDto update(RefreshTokenDto refreshTokenDto);

    PageResponse<RefreshTokenDto> findAll(PageRequest<RefreshTokenDto> model);

    Optional<RefreshTokenDto> findById(UUID id);

    Boolean deleteById(UUID id);


}
