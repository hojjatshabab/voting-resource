package com.ream.core.service.security;

import com.ream.core.service.security.dto.BlackListTokenDto;
import com.ream.core.service.security.dto.LogHistoryDto;

public interface BlackListTokenService {
    BlackListTokenDto save(String token);

    Boolean getByToken(String token);

}
