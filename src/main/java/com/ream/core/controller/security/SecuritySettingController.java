package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.SecuritySettingService;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.SecuritySettingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security-settings")
@Tag(name = "تعریف متدهای امنیتی سیستم", description = "تعریف متدهای امنیتی سیستم")
public class SecuritySettingController extends BaseController {

    @Autowired
    private SecuritySettingService securitySettingService;


    @PostMapping
    public ActionResult<SecuritySettingDto> save(@RequestBody SecuritySettingDto securitySettingDto, Locale locale) {
        isExist(securitySettingDto, ModeType.CREATE, locale);
        try {
            return RESULT(securitySettingService.save(securitySettingDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" no conflict ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<SecuritySettingDto> update(@RequestBody SecuritySettingDto securitySettingDto, Locale locale) {
        isExist(securitySettingDto, ModeType.EDIT, locale);
        try {
            return RESULT(securitySettingService.update(securitySettingDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("no conflict", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<SecuritySettingDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<SecuritySettingDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<SecuritySettingDto> SecuritySettingDtoPageResponse;
        try {
            SecuritySettingDtoPageResponse = securitySettingService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(SecuritySettingDtoPageResponse)) {
            return NO_CONTENT("SecuritySettingDtoPageResponse", locale);
        } else {
            return RESULT(SecuritySettingDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<SecuritySettingDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<SecuritySettingDto> optionalSecuritySettingDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalSecuritySettingDto = securitySettingService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalSecuritySettingDto.isPresent()) {
            return RESULT(optionalSecuritySettingDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!securitySettingService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(securitySettingService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(SecuritySettingDto securitySettingDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(securitySettingDto.getId())) {
                Optional<SecuritySettingDto> optionalSecuritySettingDto = securitySettingService.findById(securitySettingDto.getId());
                if (!optionalSecuritySettingDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(securitySettingDto.getFailedLoginCount())) {
            NO_CONTENT(" getFailedLoginCount ", locale);
        }
        if (Objects.isNull(securitySettingDto.getExpireDay())) {
            NO_CONTENT(" getRolePersianName ", locale);
        }


    }


}
