package com.ream.core.service.security;

import com.ream.core.domain.security.TokenList;
import com.ream.core.service.security.dto.TokenListDto;

import java.util.List;
import java.util.Optional;

public interface TokenListService {
    TokenList save(String username, String token);

    Optional<List<TokenListDto>> getUserOnline();

    Boolean logOutUserByUserName(String username);

    Boolean logOutAllUser();

}
