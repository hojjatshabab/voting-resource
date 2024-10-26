package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Permission;
import com.ream.core.domain.security.RefreshToken;
import com.ream.core.repository.security.PermissionRepository;
import com.ream.core.repository.security.RefreshTokenRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.RefreshTokenService;
import com.ream.core.service.security.dto.PermissionDto;
import com.ream.core.service.security.dto.RefreshTokenDto;
import com.ream.core.service.security.mapper.PermissionDtoMapper;
import com.ream.core.service.security.mapper.RefreshTokenDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;


    @Autowired
    RefreshTokenDtoMapper refreshTokenDtoMapper;


    public String createRefreshToken(UUID userId) {
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserId(userId);
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + (1 * 12 * 60 * 60 * 1000))); // 12 ck
        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken != null && refreshToken.getExpiryDate().after(new Date())) {
            return refreshToken;
        }
        return null;
    }

    public void deleteRefreshToken(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Scheduled(fixedRate = 3600000)
    public void cleanOldRefreshToken() {
        Instant oneHourAgo = Instant.now().minusSeconds(43200);
        refreshTokenRepository.deleteByCreationDateTimeBefore(oneHourAgo);
    }


    @Override
    public RefreshTokenDto save(RefreshTokenDto refreshTokenDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return refreshTokenDtoMapper.toDto(refreshTokenRepository.save(refreshTokenDtoMapper.toEntity(refreshTokenDto)));
    }

    @Override
    public RefreshTokenDto update(RefreshTokenDto refreshTokenDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return refreshTokenDtoMapper.toDto(refreshTokenRepository.save(refreshTokenDtoMapper.toEntity(refreshTokenDto)));

    }

    @Override
    public PageResponse<RefreshTokenDto> findAll(PageRequest<RefreshTokenDto> model) {
        List<RefreshTokenDto> result = refreshTokenRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(refreshTokenDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = refreshTokenRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<RefreshTokenDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<RefreshToken> optionalPermission = refreshTokenRepository.findById(id);
        if (optionalPermission.isPresent()) {
            return Optional.ofNullable(refreshTokenDtoMapper.toDto(optionalPermission.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        refreshTokenRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
