package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.model.Priority;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.TokenListService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.TokenListDto;
import com.ream.core.service.security.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/log-history")
@Tag(name = "log های سیتم ", description = "log های سیتم  ")
public class LogHistoryController extends BaseController {


    @Autowired
    private LogHistoryService logHistoryService;


    @PostMapping
    public ActionResult<LogHistoryDto> save(@RequestBody LogHistoryDto logHistoryDto, Locale locale) {
        isExist(logHistoryDto, ModeType.CREATE, locale);
        try {
            return RESULT(logHistoryService.save(logHistoryDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" no conflict ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @Autowired
    UserService userService;


    @PostMapping("/add-log")
    public ActionResult<Boolean> addLog(@RequestBody Log log, Locale locale) {
        try {
            UserDto userDto = userService.getCurrentUser();
            Priority priority = null;
            if (Objects.nonNull(log.state)) {
                if (log.state.equals(Priority.LOW.toString())) {
                    priority = Priority.LOW;
                } else if (log.state.equals(Priority.MEDIUM.toString())) {
                    priority = Priority.MEDIUM;

                } else if (log.state.equals(Priority.VERY_HIGH.toString())) {
                    priority = Priority.VERY_HIGH;
                } else if (log.state.equals(Priority.HIGH.toString())) {
                    priority = Priority.HIGH;
                } else
                    return NO_CONTENT("state", locale);
            } else {
                return NO_CONTENT("state", locale);
            }
            logHistoryService.setLog(Objects.nonNull(userDto) ? userDto.getUsername() : "know", log.actionName, log.description, priority);
            return RESULT(true, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @PutMapping
    public ActionResult<LogHistoryDto> update(@RequestBody LogHistoryDto logHistoryDto, Locale locale) {
        isExist(logHistoryDto, ModeType.EDIT, locale);
        try {
            return RESULT(logHistoryService.update(logHistoryDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("no conflict", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<LogHistoryDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<LogHistoryDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<LogHistoryDto> LogHistoryDtoPageResponse;
        try {
            LogHistoryDtoPageResponse = logHistoryService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(LogHistoryDtoPageResponse)) {
            return NO_CONTENT("LogHistoryDtoPageResponse", locale);
        } else {
            return RESULT(LogHistoryDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<LogHistoryDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<LogHistoryDto> optionalLogHistoryDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalLogHistoryDto = logHistoryService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalLogHistoryDto.isPresent()) {
            return RESULT(optionalLogHistoryDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }


    @GetMapping("/last-login/{number}")
    public ActionResult<Optional<List<LogHistoryDto>>> lastLogin(@PathVariable Integer number, Locale locale) {
        Optional<List<LogHistoryDto>> optionalLogHistoryDto;
        if (number.equals(null)) {
            return NO_CONTENT(" number ", locale);
        }
        try {
            UserDto dto = userService.getCurrentUser();
            if (Objects.isNull(dto)) {
                return NO_CONTENT(" user ", locale);
            }
            optionalLogHistoryDto = logHistoryService.lastLogin(dto.getUsername(), number);
            return RESULT(optionalLogHistoryDto, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }

    @Autowired
    TokenListService tokenListService;

    @GetMapping("/user-online")
    public ActionResult<Optional<List<TokenListDto>>> userOnline(Locale locale) {
        Optional<List<TokenListDto>> optionalTokenListDto;
        try {
            optionalTokenListDto = tokenListService.getUserOnline();
            return RESULT(optionalTokenListDto, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }

    @GetMapping("/user-logout/{username}")
    public ActionResult<Boolean> userLogOut(@PathVariable String username, Locale locale) {


        if (Objects.isNull(username)) return NO_CONTENT("username", locale);
        try {
            return RESULT(tokenListService.logOutUserByUserName(username), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }


    @GetMapping("/all-user-logout")
    public ActionResult<Boolean> allUserLogOut( Locale locale) {
        try {
            return RESULT(tokenListService.logOutAllUser(), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }


    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!logHistoryService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(logHistoryService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(LogHistoryDto LogHistoryDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(LogHistoryDto.getId())) {
                Optional<LogHistoryDto> optionalLogHistoryDto = logHistoryService.findById(LogHistoryDto.getId());
                if (!optionalLogHistoryDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(LogHistoryDto.getActionName())) {
            NO_CONTENT(" getActionName ", locale);
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class Log {
        String actionName;
        String description;
        String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


    }


}
