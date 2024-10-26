package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.PermissionService;
import com.ream.core.service.security.dto.LogHistoryDto;
import com.ream.core.service.security.dto.PermissionDto;
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
@RequestMapping("/api/permissions")
@Tag(name = "تعریف سطح دسترسی سیستم", description = "تعریف سطح دسترسی سیستم")
public class PermissionController extends BaseController {






    @Autowired
    private PermissionService permissionService;


    @PostMapping
    public ActionResult<PermissionDto> save(@RequestBody PermissionDto permissionDto, Locale locale) {
        isExist(permissionDto, ModeType.CREATE, locale);
        try {
            return RESULT(permissionService.save(permissionDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" key ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<PermissionDto> update(@RequestBody PermissionDto permissionDto, Locale locale) {
        isExist(permissionDto, ModeType.EDIT, locale);
        try {
            return RESULT(permissionService.update(permissionDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("key", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<PermissionDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<PermissionDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<PermissionDto> PermissionDtoPageResponse;
        try {
            PermissionDtoPageResponse = permissionService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(PermissionDtoPageResponse)) {
            return NO_CONTENT("PermissionDtoPageResponse", locale);
        } else {
            return RESULT(PermissionDtoPageResponse, locale);
        }

    }




    @GetMapping("/id/{id}")
    public ActionResult<Optional<PermissionDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<PermissionDto> optionalPermissionDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalPermissionDto = permissionService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalPermissionDto.isPresent()) {
            return RESULT(optionalPermissionDto,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!permissionService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(permissionService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(PermissionDto permissionDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(permissionDto.getId())) {
                Optional<PermissionDto> optionalPermissionDto = permissionService.findById(permissionDto.getId());
                if (!optionalPermissionDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(permissionDto.getKey())) {
            NO_CONTENT(" getKey ", locale);
        }
        if (Objects.isNull(permissionDto.getTitle())) {
            NO_CONTENT(" getTitle ", locale);
        }
        if (Objects.isNull(permissionDto.getHttpRequestMethod())) {
            NO_CONTENT(" getHttpRequestMethod ", locale);
        }

    }


}
