package com.ream.core.service.voting.impl;


import com.ream.core.domain.voting.SmsLog;
import com.ream.core.repository.voting.SmsLogRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.SmsLogService;
import com.ream.core.service.voting.dto.SmsLogDto;
import com.ream.core.service.voting.mapper.SmsLogDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SmsLogServiceImpl implements SmsLogService {

   @Autowired
   SmsLogDtoMapper smsLogDtoMapper;
    @Autowired
    SmsLogRepository smsLogRepository;

    @Override
    public SmsLogDto save(SmsLogDto smsLogDto) {
        return smsLogDtoMapper.toDto(smsLogRepository.save(smsLogDtoMapper.toEntity(smsLogDto)));
    }

    @Override
    public SmsLogDto update(SmsLogDto smsLogDto) {
        return smsLogDtoMapper.toDto(smsLogRepository.save(smsLogDtoMapper.toEntity(smsLogDto)));

    }

    @Override
    public PageResponse<SmsLogDto> findAll(PageRequest<SmsLogDto> model) {

        List<SmsLogDto> result = smsLogRepository.findAll(Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(smsLogDtoMapper::toDto).collect(Collectors.toList());
        long count = smsLogRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<SmsLogDto> findById(UUID id) {
        Optional<SmsLog> optionalSmsLog = smsLogRepository.findById(id);
        if (optionalSmsLog.isPresent()) {
            return Optional.ofNullable(smsLogDtoMapper.toDto(optionalSmsLog.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {

            return false;
        }
        smsLogRepository.deleteById(id);

        return true;
    }

    @Override
    public List<SmsLogDto> findSmsNoStatus() {
        List<SmsLog> smsLogsSndTimeIsNull = smsLogRepository.getByStatus(-1);
        return smsLogsSndTimeIsNull.size() > 0 ? smsLogDtoMapper.toDtoList(smsLogsSndTimeIsNull) : new ArrayList<>();
    }


    @Override
    public Integer findLastCode(String userName) {
        SmsLog smsLog = smsLogRepository.findTop1ByUserIdOrderByCreationDateTimeDesc(userName);

        Integer code = null;
        if (Objects.nonNull(smsLog)) {
            if (Objects.nonNull(smsLog.getCreationDateTime())) {
                Date creationDate = Date.from(smsLog.getCreationDateTime());
                Integer mm = creationDate.getMinutes();
                creationDate.setMinutes(mm + 3);
                if (Objects.nonNull(smsLog.getMessage()) && Objects.nonNull(creationDate) && creationDate.after(new Date())) {
                    String message = smsLog.getMessage();
                    Pattern integerPattern = Pattern.compile(":\\d[0-9]+");
                    Matcher matcher = integerPattern.matcher(message);
                    List<String> integerList = new ArrayList<>();
                    while (matcher.find()) {
                        integerList.add(matcher.group());
                    }
                    if (integerList.size() > 0) {
                        code = Integer.valueOf(integerList.get(0).substring(1));
                        return Objects.nonNull(code) && code > 100000 ? code : null;
                    }
                }
            }
        }

        return null;
    }
}
