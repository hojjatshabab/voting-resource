package com.ream.core.service.security;

import com.ream.core.model.Priority;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.RoleDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LogHistoryService {

    LogHistoryDto save(LogHistoryDto logHistoryDto);

    LogHistoryDto update(LogHistoryDto logHistoryDto);


    void setLog(String userName, String actionName, String description, Priority priority);

    PageResponse<LogHistoryDto> findAll(PageRequest<LogHistoryDto> model);

    Optional<LogHistoryDto> findById(UUID id);

    Optional<List<LogHistoryDto>> lastLogin(String username,Integer number);

//    Optional<List<LogHistoryDto>> userOnline();

    Boolean deleteById(UUID id);

}
