package com.ream.core.service.security.impl;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.Role;
import com.ream.core.model.Priority;
import com.ream.core.repository.security.LogHistoryRepository;
import com.ream.core.repository.security.RoleRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.mapper.LogHistoryDtoMapper;
import com.ream.core.service.security.mapper.RoleDtoMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogHistoryServiceImpl implements LogHistoryService {


    @Autowired
    LogHistoryRepository logHistoryRepository;


    @Autowired
    LogHistoryDtoMapper logHistoryDtoMapper;


    @Override
    public LogHistoryDto save(LogHistoryDto logHistoryDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return logHistoryDtoMapper.toDto(logHistoryRepository.save(logHistoryDtoMapper.toEntity(logHistoryDto)));
    }

    @Override
    public LogHistoryDto update(LogHistoryDto logHistoryDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return logHistoryDtoMapper.toDto(logHistoryRepository.save(logHistoryDtoMapper.toEntity(logHistoryDto)));

    }

    @Override
    public void setLog(String userName, String actionName, String description, Priority priority) {
        LogHistoryDto logHistoryDto = new LogHistoryDto();
        logHistoryDto.setUserName(userName);
        logHistoryDto.setActionName(actionName);
        logHistoryDto.setDescription(description);
        logHistoryDto.setPriority(priority);
        logHistoryDto.setIpAddress(getClientIp());
        logHistoryRepository.save(logHistoryDtoMapper.toEntity(logHistoryDto));
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

    @Override
    public PageResponse<LogHistoryDto> findAll(PageRequest<LogHistoryDto> model) {
        List<LogHistoryDto> result = logHistoryRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(logHistoryDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = logHistoryRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<LogHistoryDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<LogHistory> optionalUser = logHistoryRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(logHistoryDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }


    @Override
    public Optional<List<LogHistoryDto>> lastLogin(String username, Integer number) {
        List<LogHistory> list = logHistoryRepository.findByUserNameAndActionNameOrderByCreationDateTimeDesc(username, "LOGIN-SUCCESSFUL");
        if (list.size() > 0) {
            int end = Math.min(number, list.size()); // حد بالای sublist را تنظیم می‌کنیم
            // تبدیل لیست لاگ به لیست DTO و برگرداندن تعداد مشخصی از آن‌ها
            List<LogHistoryDto> result = list.subList(0, end).stream().map(logHistoryDtoMapper::toDto)
                    .collect(Collectors.toList());
            return Optional.ofNullable(result);
        }
        return Optional.empty();
    }

//    @Override
//    public Optional<List<LogHistoryDto>> userOnline() {
//        Instant oneHourAgo = Instant.now().minusSeconds(3600);
//        List<LogHistory> totalList = new ArrayList<>();
//        List<LogHistory> listLogin = logHistoryRepository.findByActionNameAndCreationDateTimeAfterOrderByCreationDateTimeDesc("LOGIN-SUCCESSFUL", oneHourAgo);
//        List<LogHistory> listLogOut = logHistoryRepository.findByActionNameAndCreationDateTimeAfterOrderByCreationDateTimeDesc("LOGOUT-SUCCESSFUL", oneHourAgo);
//        if (listLogin.size() > 0 && listLogOut.size() > 0) {
//            for (LogHistory resultLogin : listLogin) {
//                for (LogHistory resultLogOut : listLogOut) {
//                    if (!resultLogin.getUserName().equals(resultLogOut.getUserName()))
//                        totalList.add(resultLogin);
//                }
//            }
//            if (totalList.size() > 0) {
//                List<LogHistoryDto> result = totalList.stream().map(logHistoryDtoMapper::toDto)
//                        .collect(Collectors.toList());
//                return Optional.ofNullable(result);
//
//            }
//        }
//
//        if (listLogin.size() > 0) {
//            List<LogHistoryDto> result = listLogin.stream().map(logHistoryDtoMapper::toDto)
//                    .collect(Collectors.toList());
//            return Optional.ofNullable(result);
//        }
//
//        return Optional.empty();
//    }

    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        logHistoryRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
