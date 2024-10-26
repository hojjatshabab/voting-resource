package com.ream.core.service.security.impl;

import com.ream.core.domain.security.TokenList;
import com.ream.core.repository.security.SecuritySettingRepository;
import com.ream.core.repository.security.TokenListRepository;
import com.ream.core.service.security.BlackListTokenService;
import com.ream.core.service.security.TokenListService;
import com.ream.core.service.security.dto.TokenListDto;
import com.ream.core.service.security.mapper.TokenListDtoMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TokenListServiceImpl implements TokenListService {


    @Autowired
    TokenListRepository tokenListRepository;

    @Autowired
    BlackListTokenService blackListTokenService;

    @Autowired
    TokenListDtoMapper tokenListDtoMapper;


    @Override
    public TokenList save(String username, String token) {

        List<TokenList> tokenLists = tokenListRepository.findByUsername(username);
        if (tokenLists.size() > 0) {
            tokenLists.stream().forEach(f -> {
                blackListTokenService.save(f.getToken());
            });
        }
        tokenListRepository.deleteByUsername(username);
        TokenList tokens = new TokenList();
        tokens.setUsername(username);
        tokens.setToken(token);
        tokens.setIpAddress(getClientIp());
        return tokenListRepository.save(tokens);
    }


    @Autowired
    private HttpServletRequest request;

    private String getClientIp() {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


    @Scheduled(fixedRate = 10000)
    public void cleanOldTokens() {
        Instant oneHourAgo = Instant.now().minusSeconds(10800);
        tokenListRepository.deleteByCreationDateTimeBefore(oneHourAgo);
    }


    @Override
    public Optional<List<TokenListDto>> getUserOnline() {
        return Optional.ofNullable(tokenListDtoMapper.toDtoList(tokenListRepository.findAll()));
    }


    @Override
    public Boolean logOutUserByUserName(String username) {
        List<TokenList> tokenLists = tokenListRepository.findByUsername(username);
        if (tokenLists.size() > 0) {
            tokenLists.stream().forEach(f -> {
                blackListTokenService.save(f.getToken());
            });
        }
        tokenListRepository.deleteByUsername(username);
        return true;
    }


    @Override
    public Boolean logOutAllUser() {
        List<TokenList> tokenLists = tokenListRepository.findAll();
        tokenLists.stream().forEach(f->logOutUserByUserName(f.getUsername()));
        return true;
    }
}
