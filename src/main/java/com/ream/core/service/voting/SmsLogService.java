package com.ream.core.service.voting;


import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.SmsLogDto;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SmsLogService {


    SmsLogDto save(SmsLogDto smsLogDto);

    SmsLogDto update(SmsLogDto smsLogDto);

    PageResponse<SmsLogDto> findAll(PageRequest<SmsLogDto> model);

    Optional<SmsLogDto> findById(UUID id);

    Boolean deleteById(UUID id);

    List<SmsLogDto> findSmsNoStatus();

    Integer findLastCode(String userName);
}