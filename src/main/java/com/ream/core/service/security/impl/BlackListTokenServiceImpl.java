package com.ream.core.service.security.impl;

import com.ream.core.domain.security.BlackListToken;
import com.ream.core.repository.security.AccessRepository;
import com.ream.core.repository.security.BlackListTokenRepository;
import com.ream.core.service.security.BlackListTokenService;
import com.ream.core.service.security.dto.BlackListTokenDto;
import com.ream.core.service.security.mapper.AccessDtoMapper;
import com.ream.core.service.security.mapper.BlackListTokenDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlackListTokenServiceImpl implements BlackListTokenService {

    @Autowired
    BlackListTokenRepository blackListTokenRepository;


    @Autowired
    BlackListTokenDtoMapper blackListTokenDtoMapper;

    @Override
    public BlackListTokenDto save(String token) {
        BlackListTokenDto blackListTokenDto = new BlackListTokenDto();
        blackListTokenDto.setToken(token);
        return blackListTokenDtoMapper.toDto(blackListTokenRepository.save(blackListTokenDtoMapper.toEntity(blackListTokenDto)));
    }

    @Override
    public Boolean getByToken(String token) {
        Optional<List<BlackListToken>> blackListToken = blackListTokenRepository.getByToken(token);
        if (blackListToken.get().size()>0)
            return true;
        return false;
    }
    @Scheduled(fixedRate = 10800000)
    public void cleanOldTokens() {
        Instant oneHourAgo = Instant.now().minusSeconds(10800);
        blackListTokenRepository.deleteByCreationDateTimeBefore(oneHourAgo);
    }

}

